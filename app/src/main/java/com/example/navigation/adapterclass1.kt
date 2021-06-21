package com.example.navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class myadapter1(val names: List<filenames>): RecyclerView.Adapter<myadapter1.myviewholder1>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder1 {
        val inflator = LayoutInflater.from(parent.context)

        val view = inflator.inflate(R.layout.cardview1,parent,false)

        return myviewholder1(view)
    }

    override fun onBindViewHolder(holder: myviewholder1, position: Int) {
        holder.textview1.text = names[position].time.toString()
    }

    override fun getItemCount(): Int {
        return names.size
    }


    class myviewholder1(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textview1 = itemView.findViewById<TextView>(R.id.textview)
        init
        {
            itemView.setOnClickListener()
            {
                Toast.makeText(itemView.context,"you clicked item ${adapterPosition + 1}", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
