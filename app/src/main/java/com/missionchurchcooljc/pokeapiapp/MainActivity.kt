package com.missionchurchcooljc.pokeapiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    private val job: Job = Job()
    private val apiService by lazy { PokeApiService.retrofit }
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // use coroutines to avoid call adapter
        coroutineScope.launch {
            callApi()
        }
    }


    private suspend fun callApi() {
//        TODO: handle long running request
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_list)
        val response = apiService.listResults()
        Log.d("pokemon", response.pokeList.joinToString())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PokeAdapter(response)
    }
}

interface PokeApiService {

    @GET("pokemon?limit=100&offset=200")
    suspend fun listResults(): PokeResponse

    companion object {
        val retrofit: PokeApiService = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApiService::class.java)
    }
}

data class Pokemon(
    val name: String,
    val url: String
)

data class PokeResponse(
    @SerializedName("results")
    val pokeList: List<Pokemon>
)