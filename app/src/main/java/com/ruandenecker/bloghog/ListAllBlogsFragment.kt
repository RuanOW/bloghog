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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.ruandenecker.bloghog.data.BlogPost
import com.ruandenecker.bloghog.databinding.FragmentListAllBlogsBinding
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.fragment_all_blog_item.*
import kotlinx.android.synthetic.main.fragment_all_blog_item.view.*
import kotlinx.android.synthetic.main.fragment_blog_item_detail.*


class ListAllBlogsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentListAllBlogsBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = Firebase.auth
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list_all_blogs, container, false)
        db = Firebase.firestore

        val adapter = GroupAdapter<GroupieViewHolder>()
        binding.allBlogRecyclerView.setAdapter(adapter)

        adapter.setOnItemClickListener { item, view ->
            val blogI = item as BlogItem
            Log.d("ItemClicked", "Item ID ${blogI.blogItem.id}")
            val action = ListAllBlogsFragmentDirections.actionListAllBlogsFragment2ToBlogItemDetailFragment(blogI.blogItem.id)
            findNavController().navigate(action)

        }

        db.collection("blogs").get()
            .addOnSuccessListener {
                if (it != null){
                    for (blog in it) {
                        var resultBlogItem = blog.toObject<BlogPost>()
                        resultBlogItem.id = blog.id
                        Log.d("BlogItem", "ID=> ${resultBlogItem.id}")
                        adapter.add(BlogItem(resultBlogItem))
                    }
                }

            }

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

class BlogItem(val blogItem: BlogPost) : Item(){

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.mainHeading.text = blogItem.title
        viewHolder.subHeading.text = blogItem.subheading
        viewHolder.timeStamp.text = blogItem.timestamp.toDate().toString()
        if(blogItem.headerImageUrl != "" && blogItem.headerImageUrl.isNotEmpty()){
            Picasso.get().load(blogItem.headerImageUrl).fit().centerCrop().into(viewHolder.imageView);
        }
    }

    override fun getLayout(): Int = R.layout.fragment_all_blog_item

}