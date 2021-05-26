package com.example.projectandroid.ultil

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

class CheckConnection {

    companion object {
        fun haveNetworkConnection(context: Context): Boolean {
            var haveConnectedWifi = false
            var haveConnectedMobile = false
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.allNetworkInfo
            for (ni in netInfo) {
                if (ni.typeName.equals(
                        "WIFI",
                        ignoreCase = true
                    )
                ) if (ni.isConnected) haveConnectedWifi = true
                if (ni.typeName.equals(
                        "MOBILE",
                        ignoreCase = true
                    )
                ) if (ni.isConnected) haveConnectedMobile = true
            }
            return haveConnectedWifi || haveConnectedMobile
        }

        fun ShowToast_Short(context: Context?, notification: String?) {
            Toast.makeText(context, notification, Toast.LENGTH_SHORT).show()
        }
    }
}
