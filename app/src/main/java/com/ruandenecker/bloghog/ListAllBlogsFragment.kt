package com.ruandenecker.bloghog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ruandenecker.bloghog.databinding.FragmentListAllBlogsBinding


class ListAllBlogsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentListAllBlogsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = Firebase.auth
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list_all_blogs, container, false)
//        val navController = findNavController(R.id.registerFragment)
        auth.addAuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser == null){
                Log.d("Authentication", "This use is not logged in")
                findNavController().navigate(R.id.loginFragment)
            }
        }

        binding.createBlogPost.setOnClickListener {
            findNavController().navigate(R.id.action_listAllBlogsFragment2_to_writeBlogFragment)
        }


        return binding.root
    }

}