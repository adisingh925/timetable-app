package com.app1.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [setting.newInstance] factory method to
 * create an instance of this fragment.
 */
class setting : Fragment() {
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
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onStart() {
        super.onStart()

        val db = Firebase.firestore

        val auth = Firebase.auth

        var num = 0;

        val switch = view?.findViewById<Switch>(R.id.switch1)

        val x = db.collection("userdata").document("explore")

        x.get().addOnSuccessListener()
        {
                document->
            num = document.getString(auth.uid.toString())?.toInt()!!

            if(document.getString("status$num") == "false")
            {
                if (switch != null) {
                    switch.isChecked = false
                }
            }
            else
            {
                if (switch != null) {
                    switch.isChecked = true
                }
            }
        }

        switch?.setOnClickListener()
        {
            if (switch.isChecked) {
                var update = hashMapOf("status$num" to "true")
                x.set(update, SetOptions.merge())
            } else {
                var update = hashMapOf("status$num" to "false")
                x.set(update, SetOptions.merge())
            }
        }
    }
}