package com.inhyuck.examples.kotlinnews.api
import retrofit2.Call
import retrofit2.http.GET

interface RadditService {
    @GET("r/kotlin/.json")
    fun getFeedList():Call<FeedListResponse>
}