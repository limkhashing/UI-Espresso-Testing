package com.limkhashing.testdrivendevelopment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_second.*

const val GALLERY_REQUEST_CODE = 200

class SecondActivity : AppCompatActivity() {

    companion object {
        fun buildToastMessage(name: String): String {
            return "Your name is $name."
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        button_back.setOnClickListener {
            onBackPressed()
        }

        button_fragment_activity.setOnClickListener {
            val intent = Intent(this, FragmentActivity::class.java)
            startActivity(intent)
        }

        button_open_gallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
        }

        button_open_dialog.setOnClickListener {
            showDialog()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showDialog() {
        MaterialDialog(this).show {
            input(
                waitForPositiveButton = true,
                allowEmpty = false
            ) { dialog, name ->
                setNameToTextView(name.toString())
                showToast(buildToastMessage(name.toString()))
            }
            title(R.string.text_enter_name)
            positiveButton(R.string.text_ok)
        }
    }

    private fun setNameToTextView(name: String) {
        activity_secondary_title.text = name
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let {
                Glide.with(this)
                    .load(it)
                    .into(image)
            }
        }
    }

}
