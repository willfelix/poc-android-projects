package school.cesar.kioskmodafoca

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View.*
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import school.cesar.kioskmodafoca.receiver.MyAdmin


class MainActivity : AppCompatActivity() {

    val flags = (SYSTEM_UI_FLAG_LAYOUT_STABLE
            or SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or SYSTEM_UI_FLAG_FULLSCREEN
            or SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the app into full screen mode
        window.decorView.systemUiVisibility = flags

        //Following code allow the app packages to lock task in true kiosk mode
        setContentView(R.layout.activity_main)

        imageView.setOnClickListener {
            val i = Intent(this, DetailActivity::class.java)
            startActivity(i)
        }

        textView.setOnClickListener {
            val i = Intent(this, DetailActivity::class.java)
            startActivity(i)
        }

        // get policy manager
        val myDevicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        // get this app package name
        val mDPM = ComponentName(this, MyAdmin::class.java)

        if (myDevicePolicyManager.isDeviceOwnerApp(this.packageName)) {
            // get this app package name
            val packages = arrayOf(this.packageName)
            // mDPM is the admin package, and allow the specified packages to lock task
            myDevicePolicyManager.setLockTaskPackages(mDPM, packages)
            startLockTask()
        } else {
            Toast.makeText(applicationContext, "Not owner", Toast.LENGTH_LONG).show()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            Toast.makeText(this, "Volume button is disabled", Toast.LENGTH_SHORT).show()
            return true
        }

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Toast.makeText(this, "Volume button is disabled", Toast.LENGTH_SHORT).show()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    private fun setVolumMax() {
        val am = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        am.setStreamVolume(
            AudioManager.STREAM_SYSTEM,
            am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM),
            0
        )
    }

}
