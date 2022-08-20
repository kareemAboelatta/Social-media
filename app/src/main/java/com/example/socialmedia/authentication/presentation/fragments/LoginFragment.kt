package com.example.socialmedia.authentication.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel by viewModels<ViewModelAuth>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_btn_LogIn.setOnClickListener {
            val email: String = inputTextLayoutEmail.editText?.text.toString()
            val password: String = inputTextLayoutPassword.editText?.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.signIn(email, password)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.signInState.collect {
                when (it) {
                    is Resource.Success -> {
                        progress.visibility = View.GONE
                        Log.e("LoginFragment", "success")
                        Snackbar.make(
                            requireView(), "Success", Snackbar.LENGTH_SHORT
                        ).show()
                        //navigate to home fragment
                        val options = findNavController().deleteCurrentFragmentAfterNavigate()
                        findNavController().navigateSafely(
                            AppIds.action_loginFragment_to_homeFragment,
                            navOptions = options
                        )
                    }
                    is Resource.Error -> {
                        progress.visibility = View.GONE
                        Log.e("LoginFragment", "error" + it.message)
                        Snackbar.make(
                            requireView(),  it.message.toString(), Snackbar.LENGTH_LONG
                        ).show()

                    }
                    is Resource.Loading -> {
                        Log.e("LoginFragment", "loading")
                        Snackbar.make(
                            requireView(), "loading", Snackbar.LENGTH_LONG
                        ).show()
                        progress.visibility = View.VISIBLE
                    }
                }
            }
        }


        login_btn_Register.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_registerFragment
            )
        }
        login_forget.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_resetPasswordFragment
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIfUserIsLoggedIn()
    }

    private fun checkIfUserIsLoggedIn() {
        if (viewModel.checkIfUserIsLoggedIn()){
            val options = findNavController().deleteCurrentFragmentAfterNavigate()
            findNavController().navigateSafely(
                AppIds.action_loginFragment_to_homeFragment,
                navOptions = options
            )
        }
    }

}