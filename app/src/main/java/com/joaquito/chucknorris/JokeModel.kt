package com.joaquito.chucknorris

import com.google.gson.annotations.SerializedName

data class JokeModel (
    @SerializedName("value") val joke:String
)