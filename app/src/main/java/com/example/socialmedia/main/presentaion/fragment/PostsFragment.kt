package com.example.socialmedia.main.presentaion.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialmedia.R
import com.example.socialmedia.core.utils.Resource
import com.example.socialmedia.main.data.models.Post
import com.example.socialmedia.main.presentaion.MainViewModel
import com.example.socialmedia.main.presentaion.adapter.AdapterPost
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.posts_fragment.*
import javax.inject.Inject

class PostsFragment  : Fragment(R.layout.posts_fragment) {

    @Inject
    lateinit var homeAdapter: AdapterPost

    private var postList : ArrayList<Post> = ArrayList()

    private val viewModel by viewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewSetUp()

        viewModel.getPosts()

        Log.e("HomeFragment", "im in home" )

        lifecycleScope.launchWhenStarted {

            viewModel.postsState.collect{
                when(it){
                    is Resource.Success -> {
                        home_progress_bar.visibility = View.GONE

                        Log.e("HomeFragment", ""+ it.data?.size)

                        postList = it.data as ArrayList<Post>
                        homeAdapter.setList(postList)

                    }
                    is Resource.Error -> {
                        home_progress_bar.visibility = View.GONE

                        Log.e("HomeFragment", "error" + it.message)
                        Snackbar.make(
                            requireView(), "error" + it.message, Snackbar.LENGTH_LONG
                        ).show()

                    }
                    is Resource.Loading -> {
                        Log.e("HomeFragment", "loading" )
                        home_progress_bar.visibility = View.VISIBLE
                    }
                }

            }
        }







    }



    private fun recyclerViewSetUp(){
        val linearLayout = LinearLayoutManager(activity)
        linearLayout.stackFromEnd = true
        linearLayout.reverseLayout = true
        home_rec.layoutManager=linearLayout
        homeAdapter.setList(postList)
        home_rec.adapter=homeAdapter

    }
}