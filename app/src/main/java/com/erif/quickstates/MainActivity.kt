package com.erif.quickstates

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.erif.quickstates.examples.ActAnimated
import com.erif.quickstates.examples.ActDefault
import com.erif.quickstates.examples.ActFonts
import com.erif.quickstates.examples.ActFullState
import com.erif.quickstates.examples.ActImage
import com.google.android.material.button.MaterialButton

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        intent(R.id.act_main_btnDefault, ActDefault::class.java)
        intent(R.id.act_main_btnFont, ActFonts::class.java)
        intent(R.id.act_main_btnIllustration, ActImage::class.java)
        intent(R.id.act_main_btnAnimated, ActAnimated::class.java)
        intent(R.id.act_main_btnFull, ActFullState::class.java)

    }

    private fun intent(id: Int, destination: Class<*>) {
        val btn: MaterialButton = findViewById(id)
        btn.setOnClickListener {
            val intent = Intent(this, destination)
            startActivity(intent)
        }
    }

}