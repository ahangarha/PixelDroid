package com.h.pixeldroid.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.h.pixeldroid.BuildConfig
import com.h.pixeldroid.R
import com.h.pixeldroid.api.PixelfedAPI
import com.h.pixeldroid.db.AppDatabase
import com.h.pixeldroid.db.PostEntity
import com.h.pixeldroid.db.PostViewModel
import com.h.pixeldroid.models.Post
import com.h.pixeldroid.objects.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    private lateinit var preferences: SharedPreferences
    private lateinit var feed : RecyclerView
    private lateinit var adapter : FeedRecyclerViewAdapter
    private var posts = ArrayList<Post>()
    private var postViewModel: PostViewModel? = null

    private fun setContent(newPosts : ArrayList<PostEntity>) {
        newPosts.forEach(){
            posts.add(Post(it))
        }
        adapter.setPosts(newPosts)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        postViewModel?.allPosts?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            setContent(it as ArrayList<PostEntity>)
            }
        )

        preferences = this.activity!!.getSharedPreferences(
            "${BuildConfig.APPLICATION_ID}.pref", Context.MODE_PRIVATE
        )
        feed = view.findViewById(R.id.feedList)
        feed.layoutManager = LinearLayoutManager(context)
        adapter = FeedRecyclerViewAdapter(context!!)
        feed.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pixelfedAPI = PixelfedAPI.create("${preferences.getString("domain", "")}")
        val accessToken = preferences.getString("accessToken", "")
        var statuses: ArrayList<Status>? = null

        pixelfedAPI.timelineHome("Bearer $accessToken")
            .enqueue(object : Callback<List<Status>> {
                override fun onResponse(call: Call<List<Status>>, response: Response<List<Status>>) {
                    if (response.code() == 200) {
                        statuses = response.body() as ArrayList<Status>?
                        if(!statuses.isNullOrEmpty()) {
                            val domain = preferences.getString("domain", "")

                            for (status in statuses!!) {

                                val postEnt = PostEntity(
                                    uid = 0, // auto-generate the key
                                    domain = domain,
                                    username = status.account.username,
                                    displayName = status.account.display_name,
                                    description = status.content,
                                    accountID = status.account.id,
                                    nbLikes = status.favourites_count,
                                    nbShares = status.reblogs_count,
                                    ImageURL = status.media_attachments[0].url,
                                    profileImgUrl = status.account.avatar,
                                    date = Calendar.getInstance().time
                                    )

                                postViewModel?.insertPosts(arrayListOf(postEnt))
                            }
                        }

                    }
                }

                override fun onFailure(call: Call<List<Status>>, t: Throwable) {
                    Log.e("HomeFragment", t.toString())
                }
            })
    }
}