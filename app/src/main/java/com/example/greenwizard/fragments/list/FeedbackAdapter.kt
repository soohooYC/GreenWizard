package com.example.greenwizard.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greenwizard.R
import com.example.greenwizard.model.Feedback
import com.example.greenwizard.model.News
import java.text.SimpleDateFormat
import java.util.Date

class FeedbackAdapter: RecyclerView.Adapter<FeedbackAdapter.MyViewHolder>() {

    private var ratingList = emptyList<Feedback>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define your TextViews here
        val ratingBarview: RatingBar = itemView.findViewById(R.id.ratingBarview)
        val date_View: TextView = itemView.findViewById(R.id.date_View)
        val commentDetail: TextView = itemView.findViewById(R.id.commentDetail)


        val starRowLayout: ConstraintLayout = itemView.findViewById(R.id.starRowLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rating_row, parent,false)
        )
    }

    override fun getItemCount(): Int {
        return ratingList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = ratingList[position]

        holder.ratingBarview.rating = currentItem.rating
        holder.commentDetail.text = currentItem.comment

        // Format the timestamp to a readable date string
        val formattedDate = SimpleDateFormat("yyyy-MM-dd").format(Date(currentItem.date))
        holder.date_View.text = formattedDate
    }


    fun setData(feedback: List<Feedback>) {
        this.ratingList = feedback
        notifyDataSetChanged()

    }
}