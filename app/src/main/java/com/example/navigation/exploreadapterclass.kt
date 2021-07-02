package com.example.navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class exploreadapterclass(val names: List<filenames>): RecyclerView.Adapter<exploreadapterclass.myviewholder2>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder2 {
        val inflator = LayoutInflater.from(parent.context)

        val view = inflator.inflate(R.layout.cardview1,parent,false)

        return myviewholder2(view)
    }

    override fun onBindViewHolder(holder: myviewholder2, position: Int) {
        holder.textview1.text = names[position].time.toString()

    }

    override fun getItemCount(): Int {
        return names.size
    }


    class myviewholder2(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textview1 = itemView.findViewById<TextView>(R.id.textview)
        init
        {

        }

    }
}
