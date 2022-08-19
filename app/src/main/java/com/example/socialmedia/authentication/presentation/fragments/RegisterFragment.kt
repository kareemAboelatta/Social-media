package com.example.socialmedia.authentication.presentation.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.socialmedia.R
import com.example.socialmedia.authentication.presentation.ViewModelAuth
import com.example.socialmedia.core.extentions.deleteCurrentFragmentAfterNavigate
import com.example.socialmedia.core.extentions.navigateSafely
import com.example.socialmedia.core.utils.AppIds
import com.example.socialmedia.core.utils.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

const val REQUEST_CODE_GALLERY = 1;
const val REQUEST_CODE_PERMESSION_READ_EXTERNAL_STORAGE = 0;

@AndroidEntryPoint
class RegisterFragment :Fragment(R.layout.fragment_register) {

    var uri : Uri ?=null



    private val viewModel by viewModels<ViewModelAuth>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        reg_btn_register.setOnClickListener {
            val name: String = inputTextLayoutName.editText!!.text.toString()
            val bio: String = inputTextLayoutBio.editText!!.text.toString()
            val email: String = inputTextLayoutEmail.editText!!.text.toString()
            val password: String = inputTextLayoutPassword.editText!!.text.toString()


            if (uri != null) {
                viewModel.submitData(
                    name = name,
                    bio = bio,
                    email = email,
                    password = password,
                    image = uri!!
                )
            } else {
                Toast.makeText(requireContext(), "Please select a profile picture", Toast.LENGTH_SHORT).show()
            }
        }


        lifecycleScope.launchWhenStarted {

            viewModel.validateState.collect {


                if (it.bioError != null) {
                    inputTextLayoutBio.error = it.bioError
                } else{
                    inputTextLayoutBio.isErrorEnabled = false
                }


                if (it.nameError != null) {
                    inputTextLayoutName.error = it.nameError
                }else{
                    inputTextLayoutName.isErrorEnabled = false
                }

                if (it.emailError != null) {
                    inputTextLayoutEmail.error = it.emailError
                }else{
                    inputTextLayoutEmail.isErrorEnabled = false
                }

                if (it.passwordError != null) {
                    inputTextLayoutPassword.error = it.passwordError
                }else{
                    inputTextLayoutPassword.isErrorEnabled = false
                }





            }
        }




        lifecycleScope.launchWhenStarted {
            viewModel.createUserState.collect {
                when (it) {
                    is Resource.Success -> {

                        reg_btn_register.isEnabled = true
                        reg_btn_register.text = "Register"
                        progress.visibility = View.GONE

                        Snackbar.make(
                            requireView(), "Success", Snackbar.LENGTH_LONG
                        ).show()


                        //navigate to home fragment
                        val options = findNavController().deleteCurrentFragmentAfterNavigate()

                        findNavController().navigateSafely(
                            AppIds.action_registerFragment_to_homeFragment,
                            navOptions = options
                        )

                    }
                    is Resource.Error -> {
                        Snackbar.make(
                            requireView(), "error" + it.message, Snackbar.LENGTH_LONG
                        ).show()

                        reg_btn_register.isEnabled = true
                        reg_btn_register.text = "Register"
                        progress.visibility = View.GONE

                    }
                    is Resource.Loading -> {
                        Log.e("RegisterFragment", "loading")

                        reg_btn_register.isEnabled = false
                        reg_btn_register.text = "Registering..."
                        progress.visibility = View.VISIBLE

                    }
                }
            }
        }


        reg_image_change.setOnClickListener {
            if (!hasReadExternalStoragePermission())
                requestPermission()
            else
                changeProfilePhoto()

        }



        reg_backtologin.setOnClickListener {
            findNavController().navigate(
                R.id.action_registerFragment_to_loginFragment
            )
        }

    }









    private fun changeProfilePhoto() {
        Intent(Intent.ACTION_GET_CONTENT).also {
            it.type="image/*"
            startActivityForResult(it, REQUEST_CODE_GALLERY)
        }
    }

    private fun hasReadExternalStoragePermission()= activity?.let {
        ActivityCompat.checkSelfPermission(it, Manifest.permission.READ_EXTERNAL_STORAGE)} == PackageManager.PERMISSION_GRANTED


    private fun requestPermission(){
        var permissionsToRequest= mutableListOf<String>()
        if (!hasReadExternalStoragePermission()){
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (permissionsToRequest.isNotEmpty()){
            activity?.let { ActivityCompat.requestPermissions(
                it,
                permissionsToRequest.toTypedArray(),
                REQUEST_CODE_PERMESSION_READ_EXTERNAL_STORAGE
            ) }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==REQUEST_CODE_PERMESSION_READ_EXTERNAL_STORAGE  && grantResults.isNotEmpty()){
            for (i in grantResults.indices){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Log.d("PermissionRequest", "${permissions[i]} granted.")
                }
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK && requestCode == REQUEST_CODE_GALLERY){
            uri=data?.data
            reg_image.setImageURI(uri)
        }
    }
}