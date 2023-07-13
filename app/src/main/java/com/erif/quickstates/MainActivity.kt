package com.erif.quickstates

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.erif.quickstates.examples.ActImage
import com.google.android.material.button.MaterialButton

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Default
        // Font
        intent(R.id.act_main_btnIllustration, ActImage::class.java)
        // Animated

    }

    private fun intent(id: Int, destination: Class<*>) {
        val btn: MaterialButton = findViewById(id)
        btn.setOnClickListener {
            val intent = Intent(this, destination)
            startActivity(intent)
        }
    }

}