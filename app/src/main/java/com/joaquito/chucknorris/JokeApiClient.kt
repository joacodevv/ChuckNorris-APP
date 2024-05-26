package com.joaquito.chucknorris

import retrofit2.Response
import retrofit2.http.GET

interface JokeApiClient {
    @GET("jokes/random")
    suspend fun getRandomJoke():Response<JokeModel>


}