package com.example.projectandroid

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

private const val BASE_URL = "http://genxshopping.herokuapp.com"

//private val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build()
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()


interface APIService {
    @GET("/product?item=shoes")
    fun getAllData(): Call<List<Product>>

    @GET("/product")
    fun getProduct(@Query("item") item: String?): Call<List<Product>>

    @POST("/signup")
    suspend fun signUpUser(@Body requestBody: RequestBody): Response<SimpleJSONModel>

    @POST("/signin")
    suspend fun signInUser(@Body requestBody: RequestBody): Response<SimpleJSONModel>

    @GET("/showdb")
    suspend fun getEmployees(): Response<ResponseBody>

    @GET("/product")
    suspend fun getProducts(@Query("item") item: String?): Response<ResponseBody>

//    @GET("/product")
//    suspend fun  listProduct(
//            @Query("item") item: String
//    ): NowPlayingMoviesResp


}

object Api {
    val retrofitService: APIService by lazy{retrofit.create(APIService::class.java)}
}