package com.ruandenecker.bloghog

import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.DateTime
import com.ruandenecker.bloghog.data.BlogPost
import com.ruandenecker.bloghog.databinding.FragmentWriteBlogBinding
import java.util.*


class WriteBlogFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentWriteBlogBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        auth = Firebase.auth
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_blog, container, false)
        db = Firebase.firestore

        binding.PublishBlog.setOnClickListener {

            val blog = hashMapOf(
                "userId" to auth.currentUser?.uid,
                "timestamp" to Calendar.getInstance().time,
                "title" to binding.blogMainHeading.text.toString(),
                "subheading" to binding.blogSubHeading.text.toString(),
                "body" to binding.blogBodyCopy.text.toString()
            )
//            val blogdata = BlogPost(
//                auth.currentUser?.uid ?: "",
//                Timestamp.now(),
//                binding.blogMainHeading.text.toString(),
//                binding.blogSubHeading.text.toString(),
//                binding.blogBodyCopy.text.toString()
//            )

            db.collection("blogs")
                .add(blog)
                .addOnFailureListener {
                    Log.d("blog", "Failed to post the blog")
                    Toast.makeText(context, "Failed to post blog", Toast.LENGTH_SHORT).show()
                }
                .addOnSuccessListener {
                    Log.d("blog", "The blog was posted successfully")
                    Toast.makeText(context, "You blog has been posted", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_writeBlogFragment_to_listAllBlogsFragment2)
                }
        }

        binding.cancelBlogPost.setOnClickListener {
            findNavController().navigate(R.id.action_writeBlogFragment_to_listAllBlogsFragment2)
        }


        return binding.root
    }

}