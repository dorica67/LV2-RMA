package com.hunt.computingqoutes

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_add_person.*
import kotlinx.android.synthetic.main.activity_main.*


class AddPerson : AppCompatActivity() {
    private val CAMERA_REQUEST_CODE = 100;
    private val STORAGE_REQUEST_CODE = 101
    //image pick code
    private val IMAGE_PICK_CAMERA_CODE = 102
    private val IMAGE_PICK_GALLERY_CODE = 103

    private lateinit var cameraPermission : Array<String>
    private lateinit var StoragePermission : Array<String>

    private var imageUri:Uri? = null

    private var name:String? = ""
    private var dob:String? = ""
    private var death:String? = ""
    private var description:String? = ""
    private var qoute:String? = ""
    lateinit var dbHelper: MyDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_person)


        cameraPermission = arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        StoragePermission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
       dbHelper = MyDBHelper(this)
        picImage.setOnClickListener {

            imagePickDialog()
        }
        saveBtn.setOnClickListener {

            inputData()
        }
    }

    private fun inputData() {
        name = ""+etName.text.toString().trim()
        dob = ""+etDob.text.toString().trim()
        death = ""+etDeath.text.toString().trim()
        description = ""+etDiscription.text.toString().trim()
        qoute = ""+etQoute.text.toString().trim()

        val id = dbHelper.insertRecord(
            ""+name,
            ""+imageUri,
            ""+dob,
            ""+death,
            ""+description,
            ""+qoute,
        )
        Toast.makeText(this,"Record Added against ID $id",Toast.LENGTH_SHORT).show()



    }

    private fun imagePickDialog() {
        val options = arrayOf("Camera","Gallery")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pick Image")
        builder.setItems(options){dialog,which ->
            if (which == 0)
            {
                //camera clicked
                if (!checkCamerPermission())
                {
                    requestCameraPermission()
                }
                else
                {
                    pickFromCamera()
                }

            }
            else
            {
                //gallery clicked
                if (!checkStoragePermission())
                {
                    requestStoragePermission()
                }
                else
                {
                    pickFromGallery()
                }
            }
        }
        builder.show()
    }


    private fun checkStoragePermission(): Boolean {
        return  ContextCompat.checkSelfPermission(this,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    }

    private fun requestStoragePermission()
    {

        ActivityCompat.requestPermissions(this,StoragePermission,STORAGE_REQUEST_CODE)
    }
    private fun pickFromGallery()
    {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent,IMAGE_PICK_GALLERY_CODE)
    }
    private fun checkCamerPermission():Boolean {

        val result = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        )== PackageManager.PERMISSION_GRANTED
        val resultsq = ContextCompat.checkSelfPermission(this,
        android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

        return  result && resultsq
    }
    private fun requestCameraPermission()
    {
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE)

    }
    private fun pickFromCamera()
    {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE,"Image Title")
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image Description")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode)
        {
            CAMERA_REQUEST_CODE->{

                if (grantResults.isNotEmpty())
                {
                    val  cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    if (cameraAccepted && storageAccepted)
                    {

                        pickFromCamera()
                    }
                    else
                    {
                        Toast.makeText(this,"camera and storage permissions are required",Toast.LENGTH_SHORT).show()

                    }

                }
            }
            STORAGE_REQUEST_CODE->{
                if (grantResults.isNotEmpty())
                {
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED

                    if (storageAccepted)
                    {
                        pickFromGallery()
                    }
                    else
                    {
                        Toast.makeText(this," storage permissions is required",Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode ==Activity.RESULT_OK)
        {
            if (requestCode == IMAGE_PICK_GALLERY_CODE)
            {
                CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this)
            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this)
            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
            {
                val  result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK)
                {
                    val resultUri = result.uri
                    imageUri = resultUri
                    picImage.setImageURI(resultUri)
                }
                else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
                {
                    val error = result.error
                    Toast.makeText(this ,""+error,Toast.LENGTH_SHORT).show()
                }
            }


        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}