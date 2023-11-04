package br.com.willfelix.theone

import android.annotation.SuppressLint
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import android.app.Activity
import android.content.Intent
import android.content.ComponentName
import android.os.UserHandle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Glide.with(this)
            .load(R.drawable.theone)
            .asGif()
            .placeholder(R.drawable.theone)
            .into( theonegif )

//        showImei()

        theonegif.setOnClickListener { secure() }

        fingerinfo()

    }

    fun fingerinfo() {
        val SystemProperties = Class.forName("android.os.SystemProperties")
        val method = SystemProperties.getMethod(
            "get",
            String::class.java, String::class.java
        )

        val finger = method.invoke(null, "ro.build.fingerprint", "")
        imeiTxt.text = finger?.toString()
    }

    fun secure(): Boolean {
        try {

            Toast.makeText(this, "SECURE INIT", Toast.LENGTH_LONG).show()

            val lockPatternUtilsClass = Class.forName("com.android.internal.widget.LockPatternUtils")
            val lockPatternUtils = lockPatternUtilsClass.getConstructor(Context::class.java).newInstance(this)
            val method = lockPatternUtilsClass.getMethod(
                "saveLockPassword",
                String::class.java, String::class.java,
                Int::class.java, Int::class.java
            )

            method.invoke(lockPatternUtils, "seilamodafoca1234", "", DevicePolicyManager.PASSWORD_QUALITY_SOMETHING, android.os.Process.myUid())

            Toast.makeText(this, "SECURE LOCKED", Toast.LENGTH_LONG).show()

        } catch (ex: Exception) {
            Toast.makeText(this, "SECURE BAD:" + ex.message, Toast.LENGTH_LONG).show()
            ex.printStackTrace()
        }

        return true
    }

    @SuppressLint("MissingPermission")
    fun showImei() {

        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var imei = telephonyManager.imei
            Toast.makeText(this, "IMEI", Toast.LENGTH_LONG).show()

            imeiTxt.text = imei
        } else {
            var imei = telephonyManager.deviceId
            Toast.makeText(this, "DEVICE ID", Toast.LENGTH_LONG).show()

            imeiTxt.text = imei
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            99 -> if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(
                    this@MainActivity,
                    "You have enabled the Admin Device features",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Problem to enable the Admin Device features",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
