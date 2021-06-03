package com.example.projectandroid.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectandroid.R
import com.example.projectandroid.ultil.GenX
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class LoginActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    var btndangki: Button? = null
    var btnDangNhap: Button? = null
    var edtEmail: EditText? = null
    var edtPassword: EditText? = null
    var edtTK: EditText? = null
    var edtMK: EditText? = null
    var edtUsername: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dangnhap1)
        btndangki = findViewById(R.id.signUpHere)
        btnDangNhap = findViewById(R.id.btn_signin)
        edtTK = findViewById(R.id.inputuser)
        edtMK = findViewById(R.id.inputpass)
        auth = FirebaseAuth.getInstance()


        btndangki?.setOnClickListener(View.OnClickListener { signup() })
        btnDangNhap?.setOnClickListener(View.OnClickListener {
            val taikhoan = edtTK?.getText().toString()
            val matkhau = edtMK?.getText().toString()
            if (taikhoan.isEmpty() || matkhau.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Thiếu thông tin đăng nhập", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Dangnhap()
            }
        })
    }

    private fun signup() {
//        val dialog = Dialog(this)
//        dialog.setContentView(R.layout.signup)
//        edtEmail = dialog.findViewById(R.id.inputemail)
//        edtPassword = dialog.findViewById(R.id.password)
//        edtUsername = dialog.findViewById(R.id.inputusersup)
//        val btnDangkiXacNhan = dialog.findViewById<Button>(R.id.signupnha)
        setContentView(R.layout.signup)
        edtEmail = findViewById(R.id.inputemail)
        edtPassword = findViewById(R.id.password)
        edtUsername = findViewById(R.id.inputusersup)
        val btnDangkiXacNhan = findViewById<Button>(R.id.signupnha)
        btnDangkiXacNhan.setOnClickListener {
            DangKi()
//            dialog.dismiss()
        }
//        dialog.setTitle("Đăng kí Hệ Thống")
//        dialog.setCanceledOnTouchOutside(false)
//        dialog.show()
    }

    private fun DangKi() {
        val dialog = Dialog(this)
        val email = edtEmail!!.text.toString()
        val password = edtPassword!!.text.toString()
        dialog.setTitle("Vui lòng đợi giây lát");
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        auth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this@LoginActivity, "Đăng kí thành công", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    dangkiBangUser()
                } else {
                    Toast.makeText(this@LoginActivity, "Lõi Đăng kí", Toast.LENGTH_SHORT).show()
                }

                // ...
            }
    }

    private fun dangkiBangUser() {
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, GenX.insertUser,
            Response.Listener { response ->
//                if (response.trim { it <= ' ' } == "success") {
//                    Toast.makeText(this@LoginActivity, "Thêm User thành công", Toast.LENGTH_SHORT)
//                        .show()
//                } else {
//                    Toast.makeText(this@LoginActivity, "Add User", Toast.LENGTH_SHORT).show()
//                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@LoginActivity, "Da xay ra loi", Toast.LENGTH_SHORT).show()
                Log.d("vpq", "Loi!\n$error")
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["email"] = edtEmail!!.text.toString()
                params["username"] = edtUsername!!.text.toString()
                return params
            }
        }
        requestQueue.add(stringRequest)
    }

    private fun Dangnhap() {
        val email = edtTK!!.text.toString()
        val password = edtMK!!.text.toString()
        auth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    Log.d("vpq", "signInWithEmail:success")
                    Toast.makeText(this@LoginActivity, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show()
                    val intent = Intent()
                    intent.putExtra("email", email)
                    setResult(RESULT_OK, intent)
                    finish()

                } else {
                    Toast.makeText(this@LoginActivity, "Lỗi ĐĂNG NHẬP", Toast.LENGTH_SHORT).show()
                }

            }
    }

}