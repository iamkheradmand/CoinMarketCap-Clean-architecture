package com.example.data.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

data class GetInfoResponse(
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("symbol")
    @Expose
    val symbol: String,
    @SerializedName("logo")
    @Expose
    val logo: String,
    @SerializedName("description")
    @Expose
    val description: String,
    @SerializedName("date_added")
    @Expose
    val date_added: String,
    @SerializedName("date_launched")
    @Expose
    val date_launched: String,
    @SerializedName("slug")
    @Expose
    val slug: String,
    @SerializedName("urls")
    @Expose
    val urls: UrlsResponseModel,
)

data class UrlsResponseModel(
    @SerializedName("website")
    @Expose
    val website: ArrayList<String>,
    @SerializedName("twitter")
    @Expose
    val twitter: ArrayList<String>,
    @SerializedName("reddit")
    @Expose
    val reddit: ArrayList<String>,
)