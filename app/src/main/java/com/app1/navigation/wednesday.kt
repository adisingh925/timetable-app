package com.app1.navigation

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [wednesda.newInstance] factory method to
 * create an instance of this fragment.
 */
class wednesday : Fragment() {
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
        return inflater.inflate(R.layout.fragment_monday, container, false)
    }

    override fun onStart() {
        super.onStart()

        var db = Firebase.firestore

        var auth = Firebase.auth

        var timetabledata = mutableListOf<dataclass>()

        var rcv1 = myadapter(timetabledata)

        lateinit var subject: String
        lateinit var time: String

        val textvieww = view?.findViewById<TextView>(R.id.textview1)
        if (textvieww != null) {
            textvieww.text = "Wednesday"
        }


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////
        fun calltimepicker(textview: TextView) {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textview.text = SimpleDateFormat("hh:mm a").format(cal.time)
            }
            TimePickerDialog(
                this.context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()
        }
////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////
        var data = db.collection("userdata").document("userdata").collection(auth.uid!!)
            .document("wednesday")
        ///////////////////////////////////////////////////////////////////

        var i = 1

        view?.findViewById<FloatingActionButton>(R.id.attach_file)?.setOnClickListener()
        {
            var intent = Intent(this.context, monday_data::class.java)
            startActivity(intent)
        }

        var putdata = db.collection("userdata").document("userdata").collection(auth.uid!!)
            .document("wednesday")

        putdata.get().addOnSuccessListener()
        { document ->
            if (document.exists()) {
                for (m in 1..10) {
                    if (document.getString("subject$m") != null) {
                        var d1 = document.getString("subject$m").toString()
                        var d2 = document.getString("time$m").toString()
                        timetabledata.add(dataclass(d1, d2))
                        rcv1.notifyDataSetChanged()
                        i++
                    }
                }
            }

        }.addOnFailureListener()
        {
            Toast.makeText(this.context, "unable to fetch data", Toast.LENGTH_SHORT).show()
        }


        var rcv = view?.findViewById<RecyclerView>(R.id.recyclerview)

        var back = view?.findViewById<ImageButton>(R.id.imageview)

        var add = view?.findViewById<ImageView>(R.id.floatingActionButton)

        if (add != null) {
            add.setOnClickListener()
            {

                var viewgroup: ViewGroup? = view?.findViewById(android.R.id.content)
                var builder = AlertDialog.Builder(this.context)
                var view1: View =
                    LayoutInflater.from(this.context).inflate(R.layout.customdialog, viewgroup, false)
                builder.setCancelable(false)
                builder.setView(view1)

                var close = view1.findViewById<Button>(R.id.cancel)
                var done = view1.findViewById<Button>(R.id.done)
                var edittext1 = view1.findViewById<EditText>(R.id.edittext1)
                var button = view1.findViewById<Button>(R.id.button2)

                var alertDialog: AlertDialog = builder.create()
                alertDialog.show()

                close.setOnClickListener()
                {
                    alertDialog.dismiss()
                }

                button.setOnClickListener()
                {
                    calltimepicker(button)
                }

                done.setOnClickListener()
                {
                    alertDialog.dismiss()
                    subject = edittext1.text.toString()
                    time = button.text.toString()
                    var data1 = hashMapOf("subject$i" to subject, "time$i" to time)
                    db.collection("userdata").document("userdata").collection(auth.uid!!)
                        .document("wednesday")
                        .set(data1, SetOptions.merge())
                    timetabledata.add(dataclass(subject, time))
                    rcv1.notifyDataSetChanged()

                    i++;

                }
            }
        }

        if (rcv != null) {
            rcv.adapter = rcv1
        }
        if (rcv != null) {
            rcv.layoutManager = LinearLayoutManager(this.context)
        }

        val swipeflag = ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        val dragflag = ItemTouchHelper.UP or ItemTouchHelper.DOWN

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(dragflag, swipeflag) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val startposition = viewHolder.adapterPosition
                val endposition = target.adapterPosition
                Collections.swap(timetabledata, startposition, endposition)
                rcv1.notifyItemMoved(startposition, endposition)

                var deletedata =
                    db.collection("userdata").document("userdata").collection(auth.uid!!)
                        .document("wednesday")
                deletedata.delete()
                i = 1

                val rewritedata =
                    db.collection("userdata").document("userdata").collection(auth.uid!!)
                        .document("wednesday")
                for (m in 1..(timetabledata.lastIndex + 1)) {
                    val update = hashMapOf(
                        "subject$m" to timetabledata[m - 1].name,
                        "time$m" to timetabledata[m - 1].time
                    )
                    rewritedata.set(update, SetOptions.merge())
                    i++
                }
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val temp = position + 1
                timetabledata.removeAt(position)

                rcv1.notifyItemRemoved(position)
                rcv1.notifyDataSetChanged()
                var deletedata =
                    db.collection("userdata").document("userdata").collection(auth.uid!!)
                        .document("wednesday")
                deletedata.delete()
                i = 1

                val rewritedata =
                    db.collection("userdata").document("userdata").collection(auth.uid!!)
                        .document("wednesday")
                for (m in 1..(timetabledata.lastIndex + 1)) {
                    val update = hashMapOf(
                        "subject$m" to timetabledata[m - 1].name,
                        "time$m" to timetabledata[m - 1].time
                    )
                    rewritedata.set(update, SetOptions.merge())
                    i++
                }
            }
        }).attachToRecyclerView(rcv)
    }
}
