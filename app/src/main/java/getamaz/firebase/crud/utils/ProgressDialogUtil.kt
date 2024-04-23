package getamaz.firebase.crud.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object ProgressDialogUtil {

    private var progressDialog: ProgressDialogFragment? = null

    fun AppCompatActivity.showProgressDialog() {
        progressDialog = ProgressDialogFragment()
        progressDialog?.show(this.supportFragmentManager, "progressDialog")
    }

    fun AppCompatActivity.dismissProgressDialog() {
        progressDialog?.dismiss()
        progressDialog = null
    }
}
