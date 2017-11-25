package io.d3stud.devfest.Data.Local.Posts;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import io.d3stud.devfest.Data.Model.Post;

/**
 * Created by thedude61636 on 11/25/17.
 */

//this is an abstract class that returns just the daos and you can also do migrations here
@Database(entities = {Post.class}, version = 1)
public abstract class PostDatabase extends RoomDatabase {

    //this creates returns the dao
    public abstract PostDao postDao();
}