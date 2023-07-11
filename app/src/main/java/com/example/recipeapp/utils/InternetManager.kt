package com.example.recipeapp.utils

import android.app.Activity
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat

class InternetManager {
    fun checkInternet(mContext: Context): Boolean {
        val connectionManager =
            mContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectionManager.activeNetworkInfo
        return if (networkInfo?.isConnected != null)
            networkInfo.isConnected
        else
            false
    }
    fun showAlertDialog(mContext: Context) {
        val alertDialog = AlertDialog.Builder(mContext)
        alertDialog.setTitle("Check your internet")
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("Open Internet") { _: DialogInterface, _: Int ->
            val internetIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            mContext.startActivity(internetIntent)
            ActivityCompat.finishAffinity(mContext as Activity)
        }
        alertDialog.setNegativeButton("Exit") { _: DialogInterface, _: Int ->
            ActivityCompat.finishAffinity(mContext as Activity)
        }
        alertDialog.show()
    }
}