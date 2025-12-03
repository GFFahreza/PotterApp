package com.galih254.potterapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.galih254.potterapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Harry Potter Books"

        binding.rvBooks.layoutManager = LinearLayoutManager(this)

        fetchBooks()
    }

    private fun fetchBooks() {
        binding.progressBar.visibility = View.VISIBLE

        val retrofit = Retrofit.Builder()
            .baseUrl("https://potterapi-fedeperin.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        api.getBooks().enqueue(object : Callback<List<Book>> {
            override fun onResponse(
                call: Call<List<Book>>,
                response: Response<List<Book>>
            ) {
                binding.progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val books = response.body() ?: emptyList()
                    Log.d("MainActivityCallEndPoint", response.body().toString())

                    adapter = BookAdapter(books) { book ->
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.putExtra("BOOK_DATA", book)
                        startActivity(intent)
                    }
                    binding.rvBooks.adapter = adapter
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Failed to load books",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}