package com.limkhashing.testdrivendevelopment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

const val REQUEST_IMAGE_CAPTURE = 200
const val KEY_IMAGE_DATA = "data"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            message.text = "Welcome, " + last_name.text + " " + first_name.text
        }

        hide_button.setOnClickListener {
            message.visibility = View.GONE
        }

        next.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        button_camera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
            data?.extras.let {
                if (it == null || !it.containsKey(KEY_IMAGE_DATA)) {
                    return
                }
                val imageBitmap = it[KEY_IMAGE_DATA] as Bitmap?
                image.setImageBitmap(imageBitmap)
            }
        }
    }
}
