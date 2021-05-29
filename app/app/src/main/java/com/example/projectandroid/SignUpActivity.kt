package com.example.projectandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {
    private lateinit var btnSignUp : Button
    private lateinit var edtUsername : EditText
    private lateinit var edtPassword : EditText
    private lateinit var edtEmail : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)
        findViewById()
        btnSignUp.setOnClickListener{
            signUp()
        }
    }
    private fun findViewById(){
        btnSignUp = findViewById(R.id.btn_sign_up)
        edtEmail = findViewById(R.id.edt_email)
        edtUsername = findViewById(R.id.edt_username)
        edtPassword = findViewById(R.id.edt_password)
    }

    private fun signUp() {
        // Create Retrofit
        val retrofit = Retrofit.Builder()
                .baseUrl("http://genxshopping.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)

        ///them vao
        val username = edtUsername?.text.toString().trim()
        val email = edtEmail?.text.toString().trim()
        val password = edtPassword?.text.toString().trim()
        ///
        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("email", email)
        jsonObject.put("password", password)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.signUpUser(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val item = response.body()
                    // Convert raw JSON to pretty JSON using GSON library
//                    val gson = GsonBuilder().setPrettyPrinting().create()
//                    val prettyJson = gson.toJson(
//                            JsonParser.parseString(
//                                    response.body()
//                                            ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
//                            )
//                    )
                    val status = item?.status ?: "success"
                    Log.d("-----Pretty Printed JSON------ :", status)
                    if(status == "success"){
                        val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                        startActivity(intent)
                    } else{
                        Toast.makeText(this@SignUpActivity, status, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }

}