package com.app1.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [explor.newInstance] factory method to
 * create an instance of this fragment.
 */
class explore : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onStart() {
        super.onStart()

        val db = Firebase.firestore

        val auth = Firebase.auth

        val back = view?.findViewById<ImageView>(R.id.imageview)

        val usernames = mutableListOf<exploredataclass>()

        var rcv1 = exploreadapterclass(usernames)

        var rcv = view?.findViewById<RecyclerView>(R.id.recyclerview)

        if (rcv != null) {
            rcv.adapter = rcv1
        }

        if (rcv != null) {
            rcv.layoutManager = LinearLayoutManager(this.context)
        }

        val x = db.collection("userdata").document("explore")

        x.get().addOnSuccessListener()
        {
                document->

            if(document.exists())
            {
                var value = document.getString("count")?.toInt()

                for(i in 1..value!!)
                {
                        var names = document.getString("username$i")
                        var status = document.getString("status$i")
                        var imagepath = document.getString("userimage$i")
                        if(imagepath == null && status == "false")
                        {
                            usernames.add(exploredataclass(names!!, "https://upload.wikimedia.org/wikipedia/commons/thumb/1/12/User_icon_2.svg/220px-User_icon_2.svg.png"))
                            rcv1.notifyDataSetChanged()
                        }
                        else if(imagepath != null && status == "false"){
                            usernames.add(exploredataclass(names!!, imagepath))
                            rcv1.notifyDataSetChanged()
                        }
                    }

                }
            }
        }
    }
