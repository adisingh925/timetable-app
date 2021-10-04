package com.app1.navigation

import android.app.*
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.karumi.dexter.Dexter.*
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.util.*

class monday_data : AppCompatActivity() {

    private var names = mutableListOf<filenames>()

    private var rcv1 = myadapter1(names)

    val storageref = FirebaseStorage.getInstance().reference

    val db = Firebase.firestore

    val auth = Firebase.auth

    lateinit var uri:Uri

    lateinit var progbar:ProgressBar

    lateinit var upload:FloatingActionButton

    lateinit var uploaded:TextView

    var value = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monday_data)

        uploaded = findViewById(R.id.textview54)

        uploaded.isVisible = false

        var rcv = findViewById<RecyclerView>(R.id.recyclerview1)

        val back = findViewById<ImageButton>(R.id.imageview)

        upload = findViewById<FloatingActionButton>(R.id.floatingActionButton2)

        progbar = findViewById(R.id.progressBar)

        progbar.isVisible = false

        rcv.adapter = rcv1
        rcv.layoutManager = LinearLayoutManager(this)

        var jumbo = db.collection("user data").document("user data").collection(globalname)
            .document("system_time")
        jumbo.get().addOnSuccessListener()
        { document ->
            if (document.exists()) {
                for (i in 1..100) {
                    if (document.getString("$i") != null) {
                        var gk = document.getString("$i").toString()
                        names.add(filenames(gk))
                        rcv1.notifyDataSetChanged()
                        value++
                    } else {
                        break
                    }
                }
            }
        }


        back.setOnClickListener()
        {
            finish()
        }

        upload.setOnClickListener()
        {
            val intent = Intent()
            intent.type = "application/pdf"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "select pdf files"),
                1212)
        }
    }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (resultCode == RESULT_OK) {
                if (data != null) {
                    uri = data.data!!
                }

                //////////////////////////////////////////////////////////////
                var viewgroup: ViewGroup = findViewById(android.R.id.content)
                var builder = AlertDialog.Builder(this)
                var view1: View =
                    LayoutInflater.from(this).inflate(R.layout.filenamedialog, viewgroup, false)
                builder.setCancelable(false)
                builder.setView(view1)

                var close = view1.findViewById<Button>(R.id.cancel)
                var done = view1.findViewById<Button>(R.id.done)
                var edittext = view1.findViewById<EditText>(R.id.edittext)
                var textview = view1.findViewById<TextView>(R.id.textview)

                var alertDialog: AlertDialog = builder.create()
                alertDialog.show()

                close.setOnClickListener()
                {
                    alertDialog.dismiss()
                }

                done.setOnClickListener()
                { view ->
                    if (edittext.text.toString().isEmpty()) {
                        Snackbar.make(view, "Please enter a filename", Snackbar.LENGTH_SHORT)
                    } else {
                        alertDialog.dismiss()
                        var syst = edittext.text.toString()
                        var riversRef = storageref.child("$globalname/$syst.pdf")
                        upload.isVisible = false
                        progbar.isVisible = true

                        riversRef.putFile(uri)
                            .addOnSuccessListener()
                            {
                                Toast.makeText(this, "Upload Success", Toast.LENGTH_SHORT).show()
                                names.add(filenames(syst))
                                rcv1.notifyDataSetChanged()
                                progbar.isVisible = false
                                upload.isVisible = true
                                val cc = hashMapOf("$value" to syst)
                                db.collection("user data").document("user data")
                                    .collection(globalname).document("system_time").set(
                                    cc,
                                    SetOptions.merge()
                                )
                                val cp = hashMapOf("value" to value)
                                db.collection("user data").document("user data")
                                    .collection(globalname).document("system_time").set(
                                    cp,
                                    SetOptions.merge()
                                )
                                storageref.child("$globalname/$syst.pdf").downloadUrl.addOnSuccessListener()
                                { uril ->
                                    var ty = hashMapOf("link${value - 1}" to uril.toString())
                                    db.collection("user data").document("user data")
                                        .collection(globalname).document("system_time").set(
                                        ty,
                                        SetOptions.merge()
                                    )
                                }
                                value++

                            }
                            .addOnFailureListener()
                            {
                                Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show()
                                progbar.isVisible = false
                                upload.isVisible = true
                            }
                            .addOnProgressListener()
                            { progress ->
                                uploaded.isVisible = true
                                uploaded.text =
                                    ((100 * progress.bytesTransferred) / (progress.totalByteCount)).toString() + "%"
                                if (uploaded.text == "100%") {
                                    uploaded.isVisible = false
                                }
                            }
                    }
                }
                //////////////////////////////////////////////////////////////
            }

        }
    }
