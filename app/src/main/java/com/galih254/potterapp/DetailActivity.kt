package com.galih254.potterapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.galih254.potterapp.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Book Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        @Suppress("DEPRECATION")
        val book = intent.getSerializableExtra("BOOK_DATA") as? Book

        book?.let {
            displayBookDetails(it)
        }
    }

    private fun displayBookDetails(book: Book) {
        binding.txtTitle.text = book.title ?: "Unknown Title"
        binding.txtOriginalTitle.text = "Original: ${book.originalTitle ?: "-"}"
        binding.txtReleaseDate.text = "Release Date: ${book.releaseDate ?: "-"}"
        binding.txtPages.text = "Pages: ${book.pages ?: "-"}"
        binding.txtDescription.text = book.description ?: "No description available"

        Glide.with(this)
            .load(book.cover)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_gallery)
            .into(binding.imgCover)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}