package com.example.popupmenu

import android.os.Bundle
import android.view.View
import android.view.MenuItem
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        popup_button.setOnClickListener {
            showPopupMenu()
        }
    }
    private fun showPopupMenu() {
        val popupMenu = PopupMenu(this, popup_button)
        popupMenu.inflate(R.menu.popupmenu)
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem ->

            when (item.itemId) {

                R.id.pants -> {
                    Toast.makeText(this, "Pants", Toast.LENGTH_SHORT).show()
                }
                R.id.shirts -> {
                    Toast.makeText(this, "Shirts", Toast.LENGTH_SHORT).show()
                }

                R.id.shoes  -> {
                    Toast.makeText(this, "Shoes", Toast.LENGTH_SHORT).show()
                }

                R.id.accessories -> {
                    Toast.makeText(this@MainActivity, "Accessories", Toast.LENGTH_SHORT).show()
                }
            }

            true
        })

    }
}