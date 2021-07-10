package com.app1.navigation

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class monday : AppCompatActivity()
{

    private val importance1 = NotificationManager.IMPORTANCE_DEFAULT

    private lateinit var notificationmanager1: NotificationManager

    private val groupId_1_id =  "group1"
    private val groupId_2_id =  "group2"
    private val groupId_1_name =  "Days_foreground_channel"
    private val groupId_2_name =  "Days_background_channel"

    private fun createnotificationchannel1()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val notificationManager = getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannelGroup(NotificationChannelGroup(groupId_1_id, groupId_1_name))

            val channel1 = NotificationChannel("12","Monday_foreground",importance1)
            channel1.description = "This is channel_foreground 1"
            channel1.group = groupId_1_id
            notificationmanager1 = getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationmanager1.createNotificationChannel(channel1)
        }
    }

    private fun createnotificationchannel2()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val notificationManager = getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannelGroup(NotificationChannelGroup(groupId_2_id, groupId_2_name))

            val channel1 = NotificationChannel("20","Monday_background",importance1)
            channel1.description = "This is channel_background 1"
            channel1.group = groupId_2_id
            notificationmanager1 = getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationmanager1.createNotificationChannel(channel1)
        }
    }

    private var db = Firebase.firestore

    private var auth = Firebase.auth

    private var timetabledata = mutableListOf<dataclass>()

    private var rcv1 = myadapter(timetabledata)

    private lateinit var subject:String
    private lateinit var time:String
    private lateinit var cali:Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monday)

        val textvieww = findViewById<TextView>(R.id.textview1)
        textvieww.text = "Monday"

        createnotificationchannel1()
        createnotificationchannel2()

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////////////////////////////////////////////////////////////////


        val build1 = NotificationCompat.Builder(this,"12")
            .setContentTitle("TimeTable")
            .setContentText("Data Updated for Monday")
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.setting)
            .setTimeoutAfter(5000)


////////////////////////////////////////////////////////////////////////////////////////////////////
        fun calltimepicker(textview: TextView)
        {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textview.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()
        }
////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////
        var data = db.collection("user data").document("user data").collection(globalname).document("monday")
        ///////////////////////////////////////////////////////////////////

        var i = 1

        var attach = findViewById<ImageView>(R.id.attach_file)

        attach.setOnClickListener()
        {
            var intent = Intent(this@monday,monday_data::class.java)
            startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        var putdata = db.collection("user data").document("user data").collection(globalname).document("monday")

        putdata.get().addOnSuccessListener()
        {
            document ->
            if(document.exists())
            {
                for(m in 1..10)
                {
                    if(document.getString("subject$m")!=null) {
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
            Toast.makeText(this,"unable to fetch data",Toast.LENGTH_SHORT).show()
        }


        var rcv = findViewById<RecyclerView>(R.id.recyclerview)

        var back = findViewById<ImageButton>(R.id.imageview)

        back.setOnClickListener()
        {
            notificationmanager1.notify(1,build1.build())
            finish()
        }

        var add = findViewById<ImageView>(R.id.floatingActionButton)

        add.setOnClickListener()
        {

            var viewgroup:ViewGroup = findViewById(android.R.id.content)
            var builder = AlertDialog.Builder(this)
            var view1:View = LayoutInflater.from(this).inflate(R.layout.customdialog,viewgroup,false)
            builder.setCancelable(false)
            builder.setView(view1)

            var close = view1.findViewById<Button>(R.id.cancel)
            var done = view1.findViewById<Button>(R.id.done)
            var edittext1 = view1.findViewById<EditText>(R.id.edittext1)
            var button = view1.findViewById<Button>(R.id.button2)

            var alertDialog:AlertDialog = builder.create()
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
                db.collection("user data").document("user data").collection(globalname).document("monday")
                    .set(data1, SetOptions.merge())
                timetabledata.add(dataclass(subject,time))
                rcv1.notifyDataSetChanged()
                setalarm(i)
                i++;

            }
        }

        rcv.adapter = rcv1
        rcv.layoutManager = LinearLayoutManager(this)

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

                var deletedata = db.collection("user data").document("user data").collection(globalname).document("monday")
                deletedata.delete()
                i=1

                val rewritedata = db.collection("user data").document("user data").collection(globalname).document("monday")
                for(m in 1..(timetabledata.lastIndex+1))
                {
                    val update = hashMapOf("subject$m" to timetabledata[m-1].name, "time$m" to timetabledata[m-1].time)
                    rewritedata.set(update, SetOptions.merge())
                    i++
                }
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val temp = position+1
                timetabledata.removeAt(position)
                cancelalarm(temp)
                rcv1.notifyItemRemoved(position)
                rcv1.notifyDataSetChanged()
                var deletedata = db.collection("user data").document("user data").collection(globalname).document("monday")
                deletedata.delete()
                i=1

                val rewritedata = db.collection("user data").document("user data").collection(globalname).document("monday")
                for(m in 1..(timetabledata.lastIndex+1))
                {
                    val update = hashMapOf("subject$m" to timetabledata[m-1].name, "time$m" to timetabledata[m-1].time)
                    rewritedata.set(update, SetOptions.merge())
                    i++
                }
            }
        }).attachToRecyclerView(rcv)
    }

    private fun setalarm(i:Int)
    {
        var ff = db.collection("user data").document("user data").collection(globalname).document("monday")

        ff.get().addOnSuccessListener {
                document ->
                if(document.getString("time$i")!=null)
                {
                    cali = Calendar.getInstance()
                    var timee = document.getString("time$i").toString()
                    var subb = document.getString("subject$i").toString()
                    var cv1 = timee[0].toString()
                    var cv2 = timee[1].toString()
                    var cv3 = cv1.toInt()
                    var cv4 = cv2.toInt()
                    cv3 *= 10
                    var hourr = cv3+cv4
                    var cv5 = timee[3].toString()
                    var cv6 = timee[4].toString()
                    var cv7 = cv5.toInt()
                    var cv8 = cv6.toInt()
                    cv7 *= 10
                    var minutee = cv7+cv8
                    cali[Calendar.DAY_OF_WEEK] = 1
                    cali[Calendar.HOUR_OF_DAY] = hourr
                    cali[Calendar.MINUTE] = minutee
                    cali[Calendar.SECOND] = 0
                    cali[Calendar.MILLISECOND] = 0

                    alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                    var intent = Intent(this, mondayalarmmanager::class.java)
                    intent.putExtra("content title","Monday Schedule")
                    intent.putExtra("content text", "You have $subb class from $timee")
                    intent.putExtra("uniqueno",i)
                    pendingIntent = PendingIntent.getBroadcast(this, i, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                    alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP, cali.timeInMillis,
                        AlarmManager.INTERVAL_DAY*7, pendingIntent
                    )
                    Toast.makeText(this, "alarm set successfully", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun cancelalarm(i:Int)
    {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,mondayalarmmanager::class.java)
        pendingIntent = PendingIntent.getBroadcast(this,i,intent,0)

        alarmManager.cancel(pendingIntent)

        Toast.makeText(this,"alarm canceled",Toast.LENGTH_SHORT).show()
    }
}