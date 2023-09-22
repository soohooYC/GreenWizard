package com.example.greenwizard.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.greenwizard.R
import com.example.greenwizard.model.RecyclePoint
import com.example.greenwizard.model.Report

class RecycleAdapter : RecyclerView.Adapter<RecycleAdapter.MyViewHolder>()  {

    private var recycleList = emptyList<RecyclePoint>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define your TextViews here
        val idView: TextView = itemView.findViewById(R.id.idView)
        val nameView: TextView = itemView.findViewById(R.id.nameView)
        val rowLayout: ConstraintLayout = itemView.findViewById(R.id.rowLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).
            inflate(R.layout.fragment_recycle_custom_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return recycleList.size
    }

    override fun onBindViewHolder(holder: RecycleAdapter.MyViewHolder, position: Int) {
        val currentItem = recycleList[position]
        holder.idView.text = currentItem.id.toString()
        holder.nameView.text = currentItem.name

        holder.rowLayout.setOnClickListener{

            val action = listRecycleDirections.actionListRecycleToUpdateRecycleFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    fun setData(recycle: List<RecyclePoint>) {
        this.recycleList = recycle
        notifyDataSetChanged()
    }

}