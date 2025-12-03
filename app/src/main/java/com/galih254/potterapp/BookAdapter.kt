package com.galih254.potterapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.galih254.potterapp.databinding.ItemBookBinding
import com.bumptech.glide.Glide

class BookAdapter(
    private val books: List<Book>,
    private val onItemClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            binding.txtTitle.text = book.title ?: "Unknown Title"
            binding.txtRelease.text = book.releaseDate ?: "Unknown Date"

            // Load cover image using Glide
            Glide.with(binding.root.context)
                .load(book.cover)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .into(binding.imgCover)

            // Set click listener
            binding.root.setOnClickListener {
                onItemClick(book)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int = books.size
}