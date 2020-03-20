package com.h.pixeldroid

import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.h.pixeldroid.db.AppDatabase
import com.h.pixeldroid.db.PostDao
import com.h.pixeldroid.db.PostEntity
import com.h.pixeldroid.db.PostViewModel
import com.h.pixeldroid.utils.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private var postTest = PostEntity(1, "test", date= Calendar.getInstance().time)
    private var postViewModel: PostViewModel? = null
/*
    @Before
    fun setup() {
        AppDatabase.TEST_MODE = true

        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

    }

    @After
    fun tearDown() {

    }

    @Test
    fun testInsertPostItem() {
        Assert.assertEquals(postTest.domain, postDao?.getById(postTest.uid)!!.domain)
    }

    @Test
    fun testDeleteAll(){
        postDao?.deleteAll()
        Assert.assertEquals(postDao?.getPostsCount(), 0)
    }

    @Test
    fun testUtilsInsertAll() {
        val postTest2 = PostEntity(2, "test", date = Calendar.getInstance().time)
        DatabaseUtils.insertAllPosts(db!!, postTest, postTest2)

        Assert.assertEquals(postTest.domain, postDao?.getById(postTest.uid)!!.domain)
        Assert.assertEquals(postTest2.domain, postDao?.getById(postTest2.uid)!!.domain)
    }

    @Test
    fun testUtilsLRU() {
        for(i in 1..db!!.MAX_NUMBER_OF_POSTS) {
            DatabaseUtils.insertAllPosts(db!!, PostEntity(i, i.toString(), date = Calendar.getInstance().time))
        }

        Assert.assertEquals("1", postDao?.getById(1)!!.domain)
        Assert.assertEquals(db?.MAX_NUMBER_OF_POSTS, postDao?.getPostsCount())

        DatabaseUtils.insertAllPosts(db!!, PostEntity(0, "0", date = Calendar.getInstance().time))
        Assert.assertEquals(db?.MAX_NUMBER_OF_POSTS, postDao?.getPostsCount())
        val eldestPost = postDao?.getById(1)
        Assert.assertEquals(null, eldestPost)
    }*/
}