package br.com.willfelix.theone

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class StartUpBootReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {

            Log.d("theone", "StartUpBootReceiver BOOT_COMPLETED");
            context?.startActivity(Intent(context, MainActivity::class.java))

        }
    }

}