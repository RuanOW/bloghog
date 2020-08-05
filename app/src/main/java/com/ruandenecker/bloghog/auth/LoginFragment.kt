package com.ruandenecker.bloghog.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ruandenecker.bloghog.R
import com.ruandenecker.bloghog.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var  binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = Firebase.auth
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.loginBtn.setOnClickListener {
            auth.signInWithEmailAndPassword(binding.LoginEmailAddress.text.toString().trim(), binding.loginPassword.text.toString().trim())
                .addOnSuccessListener {
                    findNavController().navigate(R.id.action_loginFragment_to_listAllBlogsFragment2)
                }
                .addOnFailureListener {
                    Log.d("Authentication", "The login Failed: ${it.message}")
                }
        }

        binding.gotoRegisterPage.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return binding.root
    }

}