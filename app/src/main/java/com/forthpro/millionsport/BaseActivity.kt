package com.forthpro.millionsport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.forthpro.millionsport.app.MyApplication


open class BaseActivity : AppCompatActivity() {
    private var mMyApp: MyApplication? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMyApp = this.applicationContext as MyApplication

    }

    override fun onResume() {
        super.onResume()
        mMyApp!!.setCurrentActivity(this)
    }

    override fun onPause() {
        clearReferences()
        super.onPause()
    }

    override fun onDestroy() {
        clearReferences()
        super.onDestroy()
    }

    open fun clearReferences() {
        val currActivity = mMyApp!!.getCurrentActivity()
        if (this == currActivity) mMyApp!!.setCurrentActivity(null)
    }
}