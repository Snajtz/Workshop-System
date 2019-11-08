package com.example.assemblyguide

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.object_activity.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class ObjectActivity : AppCompatActivity() {
    var editMode = false
    var bed :Bed? = null
    var db : DatabaseHelper? = null
    var currentPhotoPath = ""

    val REQUEST_TAKE_PHOTO = 1


    private fun dispatchTakePictureIntent() {

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.e("CAMERA",ex.message + ex.toString())
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.assemblyguide.provider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {

        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalStoragePublicDirectory(DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun savePic(){
        var updatedBed = ContentValues()
        updatedBed.put("ID", bed?.id)
        if(bed?.stringifyImages() == "") {
            updatedBed.put("IMAGES", currentPhotoPath)
        }
        else{
            updatedBed.put("IMAGES", "${bed?.stringifyImages()}%$currentPhotoPath")
        }
        db?.update(updatedBed)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            galleryAddPic()
            savePic()
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }

    fun loadImages(){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.object_activity)

        bed = intent.getSerializableExtra("bed") as Bed
        editTextArtNr.setText(bed?.artNr)
        editTextNotes.setText(bed?.notes)
        loadImages()
        this.db = DatabaseHelper(this)
    }

    override fun onResume() {
        var currentBed = db?.getBedById(bed?.id.toString())
        if(currentBed != null) {
            editTextArtNr.setText(currentBed.artNr)
            editTextNotes.setText(currentBed.notes)
            loadImages()
        }
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.object_activity_menu, menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId){
        R.id.editObjectBtn -> {
            if (!editMode) {
                editMode = true
                item.setIcon(R.drawable.baseline_check_black_18dp)
                editTextArtNr.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                editTextNotes.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                editTextArtNr.setTextIsSelectable(true)
                editTextNotes.setTextIsSelectable(true)
            }
            else{
                val updatedBed = ContentValues()
                updatedBed.put("ID", bed?.id)
                updatedBed.put("ARTNR", editTextArtNr.text.toString())
                updatedBed.put("NOTES", editTextNotes.text.toString())
                db?.update(updatedBed)


                editMode = false
                item.setIcon(R.drawable.baseline_edit_black_24)
                editTextArtNr.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
                editTextNotes.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
                editTextArtNr.setTextIsSelectable(false)
                editTextNotes.setTextIsSelectable(false)
            }
            true
        }
        R.id.deleteObjectBtn -> {
            db?.delete(bed?.id)
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            true
        }

        R.id.cameraObjectBtn -> {

            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
            dispatchTakePictureIntent()

            true
        }
            else -> super.onOptionsItemSelected(item)

    }

}