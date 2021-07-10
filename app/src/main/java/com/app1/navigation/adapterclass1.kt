package com.app1.navigation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
                val db = Firebase.firestore
                val auth = Firebase.auth
                var recieve = db.collection("user data").document("user data").collection(globalname).document("system_time")
                recieve.get().addOnSuccessListener()
                { document ->
                    val inte = Intent(Intent.ACTION_VIEW)
                    inte.data = Uri.parse(document.getString("link${adapterPosition+1}"))
                    val options: Bundle? = null
                    startActivity(itemView.context,inte,options)
                }
                Toast.makeText(itemView.context,"Initiating Download...", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
