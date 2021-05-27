package com.example.projectandroid.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectandroid.R
import com.example.projectandroid.model.User
import java.util.*

class InformationUserActivity : AppCompatActivity() {
    var editTextNameCustomer: EditText? = null
    var editTextdiachiCustomer: EditText? = null
    var editTextEmailCustomer: EditText? = null
    var buttonConfrim: Button? = null
    var buttonBack: Button? = null
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_user)
        init()
        showUser()
        buttonBack!!.setOnClickListener { finish() }
        buttonConfrim!!.setOnClickListener { capNhatUser() }
    }

    private fun init() {
        editTextNameCustomer = findViewById<View>(R.id.editTextNameCustomer) as EditText
        editTextdiachiCustomer = findViewById<View>(R.id.editdiachi) as EditText
        editTextEmailCustomer = findViewById<View>(R.id.editTextEmailCustomer) as EditText
        buttonConfrim = findViewById<View>(R.id.buttonConfrim) as Button
        buttonBack = findViewById<View>(R.id.buttonBack) as Button
    }

    private fun showUser() {
        val intent = intent
        val user: User? = intent.getSerializableExtra("user") as User?
        if (user != null) {
            id = user.id
        }
        if (user != null) {
            editTextNameCustomer?.setText(user.username)
        }
        if (user != null) {
            editTextdiachiCustomer?.setText(user.address)
        }
        if (user != null) {
            editTextEmailCustomer?.setText(user.email)
        }
    }

    private fun capNhatUser() {
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, "http://" + "@string/localhost" + "/server/updateUser.php",
            Response.Listener { response ->
                if (response.trim { it <= ' ' } == "success") {
                    Toast.makeText(
                        this@InformationUserActivity,
                        "Cap nhat thanh cong",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(this@InformationUserActivity, "Loi cap nhat", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    this@InformationUserActivity,
                    "Da xay ra loi,vui long thu lai",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("aaa", "Loi!\n$error")
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["idUser"] = id.toString()
                params["username"] = editTextNameCustomer!!.text.toString().trim { it <= ' ' }
                params["address"] = editTextdiachiCustomer!!.text.toString().trim { it <= ' ' }
                params["email"] = editTextEmailCustomer!!.text.toString().trim { it <= ' ' }
                return params
            }
        }
        requestQueue.add(stringRequest)
    }
}