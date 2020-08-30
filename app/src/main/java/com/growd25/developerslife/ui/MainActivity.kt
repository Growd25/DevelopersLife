package com.growd25.developerslife.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.growd25.developerslife.R
import com.growd25.developerslife.utils.addFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportFragmentManager.findFragmentById(R.id.container) == null) {
            addFragment(R.id.container, DevLifeFragment.newInstance())
        }
    }

}