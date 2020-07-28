package com.ruandenecker.bloghog.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ruandenecker.bloghog.R
import com.ruandenecker.bloghog.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = Firebase.auth
        db = Firebase.firestore
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        binding.registerBtn.setOnClickListener {
            auth.createUserWithEmailAndPassword(binding.emailAddress.text.toString().trim(), binding.password.text.toString().trim())
                .addOnCompleteListener { it: Task<AuthResult> ->
                    if(!it.isSuccessful) return@addOnCompleteListener

                    Log.d("Authentication", "The user was create successfully")
                    val uid = FirebaseAuth.getInstance().uid ?: ""
                    val user = hashMapOf(
                        "username" to username.text.toString(),
                        "email" to username.text.toString()
                    )
                    db.collection("users")
                        .document(uid)
                        .set(user)
                        .addOnSuccessListener {
                            Log.d("Authentication", "The user was create successfully")
                            findNavController().navigate(R.id.action_registerFragment_to_listAllBlogsFragment2)
                        }
                        .addOnFailureListener {
                            Log.d("Authentication", "Saving user to Database Failed: ${it.message}")
                        }

                }
                .addOnFailureListener {
                    Log.d("Authentication", "The registration failed ${it.message}")
                    Toast.makeText(context, "The registration failed ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

        return binding.root
    }


}