package com.example.projectandroid

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @POST("/signup")
    suspend fun signUpUser(@Body requestBody: RequestBody): Response<SimpleJSONModel>

    @POST("/signin")
    suspend fun signInUser(@Body requestBody: RequestBody): Response<SimpleJSONModel>

    @GET("/showdb")
    suspend fun getEmployees(): Response<ResponseBody>
}