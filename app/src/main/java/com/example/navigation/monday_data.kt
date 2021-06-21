package com.example.navigation

import android.app.*
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
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


class monday_data : AppCompatActivity() {

    private var names = mutableListOf<filenames>()

    private var rcv1 = myadapter1(names)

    val storageref = FirebaseStorage.getInstance().reference

    val db = Firebase.firestore

    val auth = Firebase.auth

    lateinit var uri:Uri

    lateinit var progbar:ProgressBar

    lateinit var upload:FloatingActionButton

    var value = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monday_data)

        var rcv = findViewById<RecyclerView>(R.id.recyclerview1)

        val back = findViewById<ImageButton>(R.id.imageview)

        upload = findViewById<FloatingActionButton>(R.id.floatingActionButton2)

        progbar = findViewById(R.id.progressBar)

        progbar.isVisible = false

        rcv.adapter = rcv1
        rcv.layoutManager = LinearLayoutManager(this)

        var jumbo = db.collection("user data").document("user data").collection(auth.uid.toString()).document("system_time")
        jumbo.get().addOnSuccessListener()
        {
    document ->
    if(document.exists())
    {
        for(i in 1..100)
        {
            if(document.getString("$i")!=null)
            {
                var gk = document.getString("$i").toString()
                names.add(filenames(gk))
                rcv1.notifyDataSetChanged()
                value++
            }
            else
            {
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
            withContext(this)
                .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse)
                    {
                        val intent = Intent()
                        intent.setType("application/pdf")
                        intent.setAction(Intent.ACTION_GET_CONTENT)
                        startActivityForResult(Intent.createChooser(intent,"select pdf files"),1212)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse)
                    {
                        val builder = AlertDialog.Builder(this@monday_data)
                            builder.setTitle("Need Permission")
                                .setMessage("This app needs permission to use this feature. You can grant them in app settings.")
                                .setPositiveButton("go to settings",DialogInterface.OnClickListener
                                {
                                    dialog, id ->

                                    val inte = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    val uri = Uri.fromParts("package",packageName,null)
                                    inte.setData(uri)
                                    startActivityForResult(inte,101)

                                })
                                .setNegativeButton("cancel",DialogInterface.OnClickListener{
                                    dialog, which ->

                                    dialog.cancel()
                                })

                        builder.show()

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?,
                    ) {
                        token?.continuePermissionRequest()
                    }
                }).check()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK)
        {
            if (data != null) {
                uri = data.data!!
            }

            var syst = System.currentTimeMillis().toString()
            var riversRef = storageref.child("uploads/$syst.pdf")
            upload.isVisible = false
            progbar.isVisible = true

            riversRef.putFile(uri)
                .addOnSuccessListener()
            {
                Toast.makeText(this,"Upload Success",Toast.LENGTH_SHORT).show()
                names.add(filenames(syst))
                rcv1.notifyDataSetChanged()
                progbar.isVisible = false
                upload.isVisible = true
                val cc = hashMapOf("$value" to syst)
                db.collection("user data").document("user data").collection(auth.uid.toString()).document("system_time").set(cc,
                    SetOptions.merge())
                val cp = hashMapOf("value" to value)
                db.collection("user data").document("user data").collection(auth.uid.toString()).document("system_time").set(cp,
                    SetOptions.merge())
                value++
            }
                .addOnFailureListener()
                {
                    Toast.makeText(this,"Upload Failed",Toast.LENGTH_SHORT).show()
                    progbar.isVisible = false
                    upload.isVisible = true
                }
        }
    }

}