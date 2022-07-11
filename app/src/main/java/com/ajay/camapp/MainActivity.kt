package com.ajay.camapp

import android.Manifest
import com.ajay.camapp.util.Utility.showLongToast
import com.ajay.camapp.util.Utility.convertBitmaptoBase64
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.content.Intent
import android.provider.MediaStore


import android.graphics.Bitmap
import android.net.Uri
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.ajay.camapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var imageInBase64: String? = null
    private var selectedImage : Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerForContextMenu(binding.profileImage)


        binding.send.setOnClickListener {
            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            val bundle = Bundle()

            bundle.putString("imageuri",selectedImage.toString())

            bundle.putString("image", imageInBase64)

            intent.putExtras(bundle)
            startActivity(intent)
        }

    }

    private fun pickImageGallery() {
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhoto, 103)
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val menuInflater: MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.menu_bar, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.camera -> {
                if (ContextCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    callCamera()
                } else {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.CAMERA),
                        1
                    )
                }
                true
            }
            R.id.gallery -> {
               askStoragePerssmission()
                true
            }
            else ->
                super.onContextItemSelected(item)
        }
    }


    private fun callCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 102)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            103 -> if (resultCode == RESULT_OK){
                selectedImage = data?.data
                binding.profileImage.setImageURI(selectedImage)
            }
            102 -> if (resultCode == RESULT_OK){
                val bitmap = data!!.extras!!["data"] as Bitmap?
            imageInBase64 = convertBitmaptoBase64(bitmap!!)
            binding.profileImage.setImageBitmap(bitmap)
            }
        }

    }

    private fun askStoragePerssmission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 104)
        } else {
            pickImageGallery()
        }
    }

}