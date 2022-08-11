package com.thefive.roomapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.thefive.roomapp.R
import com.thefive.roomapp.view.fragment.FileManagerFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.galery -> replaceFragment(FileManagerFragment())
                R.id.user ->  openActivity()
                else -> {
                }
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_Details, fragment)
        fragmentTransaction.commit()
    }
    private fun openActivity() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)

    }
}