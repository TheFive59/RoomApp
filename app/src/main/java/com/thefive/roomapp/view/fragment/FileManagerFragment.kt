package com.thefive.roomapp.view.fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.thefive.roomapp.databinding.FragmentFileManagerBinding
import kotlinx.android.synthetic.main.fragment_file_manager.*
import java.io.File

class FileManagerFragment : Fragment() {
    private var _binding: FragmentFileManagerBinding? = null
    private val binding get() = _binding!!
    private val fileResult = 1
    private val database = Firebase.database
    val myRef = database.getReference("M513")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFileManagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uploadImage.setOnClickListener {
            fileManager()//fileUpload()
        }
        downloadedImageView.setOnClickListener{
            textView2.isVisible
            downloadImages()
        }
    }

    private fun fileManager() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, fileResult)
    }

private fun downloadImages(){

    val progressDialog =ProgressDialog(activity as Activity)
    progressDialog.setMessage("Cargando")
    progressDialog.setCancelable(false)
    progressDialog.show()
    var item :String =editTextSearch.text.toString()
    val storageReference= FirebaseStorage.getInstance().getReference().child("images/image:$item")
    val localfile=File.createTempFile("tempImage","jpeg")
    storageReference.getFile(localfile).addOnSuccessListener {

       if (progressDialog.isShowing) {
           progressDialog.dismiss()
       }
        val bitmap=BitmapFactory.decodeFile(localfile.absolutePath)
       binding.downloadedImageView.setImageBitmap(bitmap)

    }.addOnFailureListener{
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
        Toast.makeText(activity as Activity,"No se encontro", Toast.LENGTH_SHORT).show()
    }
    textView2.setText("")
    editTextSearch.setText("")


}
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val progressDialog =ProgressDialog(activity as Activity)
        progressDialog.setMessage("Cargando")
        progressDialog.setCancelable(false)
        progressDialog.show()
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileResult) {
            if (resultCode == RESULT_OK) {
                val FileUri = data!!.data
                val Folder: StorageReference =
                    FirebaseStorage.getInstance().getReference().child("images")
                val file_name: StorageReference = Folder.child(FileUri!!.lastPathSegment.toString())
                file_name.putFile(FileUri).addOnSuccessListener { taskSnapshot ->
                    if (progressDialog.isShowing) {
                        progressDialog.dismiss()
                    }
                    file_name.getDownloadUrl().addOnSuccessListener { uri ->
                        val hashMap =
                            HashMap<String, String>()
                        hashMap["link"] = java.lang.String.valueOf(uri)
                        myRef.setValue(hashMap)
                        Toast.makeText(activity as Activity,"Guardada", Toast.LENGTH_SHORT).show()
                        //Log.d("MensajeADADAD", "Se subió correctamente")
                        if (progressDialog.isShowing) {
                            progressDialog.dismiss()
                        }
                    }
                }
            }
        }
    }
}