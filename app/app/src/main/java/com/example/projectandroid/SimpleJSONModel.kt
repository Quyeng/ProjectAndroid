package com.example.projectandroid

import com.google.gson.annotations.SerializedName

data class SimpleJSONModel(
    @SerializedName("status")
    var status: String?,

    @SerializedName("user_name")
    var username: String?,

    @SerializedName("id_user")
    var id: String?,

    @SerializedName("link")
    var linkimg: String?,

    @SerializedName("Error")
    var error: String?

)
