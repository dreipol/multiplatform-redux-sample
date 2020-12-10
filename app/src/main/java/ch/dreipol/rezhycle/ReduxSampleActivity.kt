package ch.dreipol.rezhycle

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

abstract class ReduxSampleActivity : AppCompatActivity() {

    override fun onBackPressed() {
        super.onBackPressed()
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val currentFocusedView: View? = currentFocus
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}