package com.example.navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class myadapter(val timetabledata: List<dataclass>): RecyclerView.Adapter<myadapter.myviewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
        val inflator = LayoutInflater.from(parent.context)

        val view = inflator.inflate(R.layout.cardview,parent,false)

        return myviewholder(view)
    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {
        holder.textview1.text = timetabledata[position].name
        holder.textview2.text = timetabledata[position].time
        //  holder.textview2.text = songs[position].string2
        /* var color = "#CCCCCC"
         if(position % 2 == 0)
         {
             color = "#EEEEEE"
         }

         holder.container.setBackgroundColor(Color.parseColor(color))*/
    }

    override fun getItemCount(): Int {
        return timetabledata.size
    }


    class myviewholder(itemView: View): RecyclerView.ViewHolder(itemView) {

       // val image1 = itemView.findViewById<ImageView>(R.id.imageview)
        val textview1 = itemView.findViewById<TextView>(R.id.textview1)
        val textview2 = itemView.findViewById<TextView>(R.id.textview2)
       // val container = itemView.findViewById<RelativeLayout>(R.id.container)

        init
        {
            itemView.setOnClickListener()
            {
                Toast.makeText(itemView.context,"you clicked item ${adapterPosition + 1}", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
