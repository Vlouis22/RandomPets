package com.driuft.randompets

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
class MainActivity : AppCompatActivity() {

    private lateinit var petList: MutableList<Pair<String, String>>
    private lateinit var rvPets: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvPets = findViewById(R.id.pet_list)
        petList = mutableListOf()

        getDogImageURL()
    }

    private fun getDogImageURL() {
        val client = AsyncHttpClient()

        client["https://dog.ceo/api/breeds/image/random/20", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val petImageArray = json.jsonObject.getJSONArray("message")
                for (i in 0 until petImageArray.length()) {
                    val imageUrl = petImageArray.getString(i)
                    val breedName = extractBreedName(imageUrl)
                    petList.add(Pair(imageUrl, breedName))
                }

                val adapter = PetAdapter(petList)
                rvPets.adapter = adapter
                rvPets.layoutManager = LinearLayoutManager(this@MainActivity)
                rvPets.addItemDecoration(
                    DividerItemDecoration(
                        this@MainActivity,
                        LinearLayoutManager.VERTICAL
                    )
                )
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }
        }]
    }

    private fun extractBreedName(imageUrl: String): String {
        val parts = imageUrl.split('/')
        return parts.getOrNull(parts.size - 2) ?: ""
    }
}
