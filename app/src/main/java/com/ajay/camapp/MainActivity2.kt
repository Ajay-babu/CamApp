package com.ajay.camapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.net.Uri
import com.ajay.camapp.databinding.ActivityMain2Binding
import com.ajay.camapp.util.Utility.convertBase64ToBitmap

class MainActivity2 : AppCompatActivity() {
    private var binding: ActivityMain2Binding? = null
    var imagevalue: String? = null
    var galleryImage: Uri?  = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val bundle = intent.extras
        if (bundle != null) {
            imagevalue = bundle.getString("image")
            val bitmap = convertBase64ToBitmap(imagevalue)
            binding!!.getImage.setImageBitmap(bitmap)

//            galleryImage = Uri.parse(bundle.getString("imageuri"))
//            binding!!.getImage.setImageURI(galleryImage)
//

        }
    }
}