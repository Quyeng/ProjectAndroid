package com.example.projectandroid

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

//team10
class dangnhap : AppCompatActivity() {
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
        setContentView(R.layout.signin)
        btndangki = findViewById(R.id.signUpHere)
        btnDangNhap = findViewById(R.id.btn_signin)
        edtTK = findViewById(R.id.inputuser)
        edtMK = findViewById(R.id.inputpass)
        auth = FirebaseAuth.getInstance()


        btndangki?.setOnClickListener(View.OnClickListener { giaoDien() })
        btnDangNhap?.setOnClickListener(View.OnClickListener {
            val taikhoan = edtTK?.getText().toString()
            val matkhau = edtMK?.getText().toString()
            if (taikhoan.isEmpty() || matkhau.isEmpty()) {
                Toast.makeText(this@dangnhap, "Thiếu thông tin đăng nhập", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Dangnhap()
            }
        })
    }

    private fun giaoDien() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.signup)
        edtEmail = dialog.findViewById(R.id.inputemail)
        edtPassword = dialog.findViewById(R.id.password)
        edtUsername = dialog.findViewById(R.id.inputusersup)
        val btnDangkiXacNhan = dialog.findViewById<Button>(R.id.signupnha)
        btnDangkiXacNhan.setOnClickListener {
            DangKi()
            dialog.dismiss()
        }
        dialog.setTitle("Đăng kí Hệ Thống")
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun DangKi() {
        val dialog = Dialog(this)
        val email = edtEmail!!.text.toString()
        val password = edtPassword!!.text.toString()
        dialog.setTitle("Tạo tài khoản mới. Vui lòng đợi giây lát");
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        auth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this@dangnhap, "Đăng kí thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@dangnhap, "Lõi Đăng kí", Toast.LENGTH_SHORT).show()
                }

                // ...
            }
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
                    Toast.makeText(this@dangnhap, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show()
                    val intent = Intent(
                        this@dangnhap,
                        HomeActivity::class.java
                    )
                    startActivity(intent)
                    intent.putExtra("email", email)
                    setResult(RESULT_OK, intent)

                    finish()

                } else {
                    Toast.makeText(this@dangnhap, "Lỗi ĐĂNG NHẬP", Toast.LENGTH_SHORT).show()
                }

            }
    }

}