package com.erif.quickstates

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.erif.quickstates.helper.Constant
import com.erif.quickstates.helper.Delay

class MainActivity: AppCompatActivity() {

    private var state: QuickState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val parentView: CoordinatorLayout = findViewById(R.id.act_main_parentLayout)
        state = QuickState(parentView, true)
        val contentLoader = findViewById<LinearLayout>(R.id.loadingLayout)
        Delay(1.5) {
            state?.show(Constant.EMPTY)
        }
        state?.contentLoader(contentLoader)
        state?.onClickListener { _: QuickStateView?, buttonState: Int, _: String? ->
            if (buttonState == QuickState.BUTTON_SINGLE) {
                /*Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);*/
                Toast.makeText(this, "State Clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

}