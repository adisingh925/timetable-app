package com.example.navigation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class exploreadapterclass(val names: List<exploredataclass>): RecyclerView.Adapter<exploreadapterclass.myviewholder2>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder2 {
        val inflator = LayoutInflater.from(parent.context)

        val view = inflator.inflate(R.layout.explorecard,parent,false)

        return myviewholder2(view)
    }

    override fun onBindViewHolder(holder: myviewholder2, position: Int) {
        holder.textview1.text = names[position].name

        Glide
            .with(holder.itemView.context)
            .load(names[position].imgpath)
            .circleCrop()
            .placeholder(R.drawable.placeholder)
            .into(holder.imageview)

    }

    override fun getItemCount(): Int {
        return names.size
    }


    class myviewholder2(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textview1 = itemView.findViewById<TextView>(R.id.textview)
        val imageview = itemView.findViewById<ImageView>(R.id.imageview)
    }
}
