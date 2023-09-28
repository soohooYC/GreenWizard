package com.example.greenwizard.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.greenwizard.R
import com.example.greenwizard.model.News
import java.text.SimpleDateFormat
import java.util.Date
import com.bumptech.glide.Glide

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    private var newsList = emptyList<News>()
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleView: TextView = itemView.findViewById(R.id.titleView)
        val dateView: TextView = itemView.findViewById(R.id.dateView)
        val newsImgView: ImageView = itemView.findViewById(R.id.newsImgView)


        val rowLayout: LinearLayout = itemView.findViewById(R.id.rowLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newsList[position]
//        holder.idView.text = currentItem.id.toString()

        if (!currentItem.imagePath.isNullOrEmpty()) {
            // Load the image using Glide
            try {
                Glide.with(holder.itemView)
                    .load(currentItem.imagePath)
                    .into(holder.newsImgView)
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle the exception (e.g., show a placeholder image or hide the ImageView)
                holder.newsImgView.visibility = View.GONE // Hide the ImageView in case of an exception
            }
        }else{
            holder.newsImgView.visibility = View.GONE // Hide the ImageView if imagePath is null or empty
        }


        holder.titleView.text = currentItem.title

        // Format the timestamp to a readable date string
        val formattedDate = SimpleDateFormat("yyyy-MM-dd").format(Date(currentItem.date))
        holder.dateView.text = formattedDate


        holder.rowLayout.setOnClickListener{
            val action = listNewsDirections.actionListNewsToUpdateNews(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    fun setData(news: List<News>) {
        this.newsList = news
        notifyDataSetChanged()
    }

    // Truncate text to a specified number of words
    private fun truncateText(text: String, maxWords: Int): String {
        val words = text.split("\\s+".toRegex())
        if (words.size > maxWords) {
            val truncatedWords = words.subList(0, maxWords)
            return truncatedWords.joinToString(" ") + "\n" + words.subList(maxWords, words.size).joinToString(" ")
        }
        return text
    }
}