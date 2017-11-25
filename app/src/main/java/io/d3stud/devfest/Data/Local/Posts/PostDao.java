package io.d3stud.devfest.Data.Local.Posts;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.d3stud.devfest.Data.Model.Post;

/**
 * Created by thedude61636 on 11/25/17.
 */
@Dao
public interface PostDao {
    //here we can implement sql queries


    //selecting all
    @Query("SELECT * FROM posts")
    LiveData<List<Post>> getAll();

    //selecting finding just one
    @Query("SELECT * FROM posts WHERE id LIKE :id LIMIT 1")
    LiveData<Post> findById(int id);

    //inserting
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);

    //removing
    @Query("DELETE FROM posts")
    void nukeTable();
}
