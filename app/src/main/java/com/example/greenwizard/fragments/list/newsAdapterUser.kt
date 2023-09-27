package com.example.greenwizard.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.greenwizard.R
import com.example.greenwizard.model.News
import java.text.SimpleDateFormat
import java.util.Date
import com.bumptech.glide.Glide

class newsAdapterUser : RecyclerView.Adapter<newsAdapterUser.MyViewHolder>() {

    private var newsList = emptyList<News>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val newstitle: TextView = itemView.findViewById(R.id.newstitle)
        val newsdate: TextView = itemView.findViewById(R.id.newsdate)
        val newsIMG: ImageView = itemView.findViewById(R.id.newsIMG)


        val rowLayoutuser: ConstraintLayout = itemView.findViewById(R.id.rowLayoutuser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.usernewsrow, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newsList[position]

        if (!currentItem.imagePath.isNullOrEmpty()) {
            // Load the image using Glide
            try {
                Glide.with(holder.itemView)
                    .load(currentItem.imagePath)
                    .into(holder.newsIMG)
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle the exception (e.g., show a placeholder image or hide the ImageView)
                holder.newsIMG.visibility = View.GONE// Hide the ImageView in case of an exception
            }
        }else{
            holder.newsIMG.visibility = View.GONE // Hide the ImageView if imagePath is null or empty
        }



        holder.newstitle.text = currentItem.title

        // Format the timestamp to a readable date string
        val formattedDate = SimpleDateFormat("yyyy-MM-dd").format(Date(currentItem.date))
        holder.newsdate.text = formattedDate

        holder.rowLayoutuser.setOnClickListener{
       val action = userNewsDirections.actionUserNewsToNewsDetail(currentItem)
        holder.itemView.findNavController().navigate(action)
        }


    }

    fun setData(news: List<News>) {
        this.newsList = news
        notifyDataSetChanged()
    }
}