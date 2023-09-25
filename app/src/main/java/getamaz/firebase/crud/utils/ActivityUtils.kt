package getamaz.firebase.crud.utils

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import android.widget.EditText
import android.widget.Toast

fun Activity.startActivity(clazz: Class<*>) {
    val intent = Intent(this, clazz)
    startActivity(intent)
}

fun Activity.isEditTextEmpty(editText: EditText): Boolean {
    return editText.text.toString().trim().isEmpty()
}

fun Activity.textFromEditText(editText: EditText): String {
    return editText.text.toString()
}


fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


fun setStatusBarAppearance(view: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val insetsController = view.windowInsetsController
        insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    }
}