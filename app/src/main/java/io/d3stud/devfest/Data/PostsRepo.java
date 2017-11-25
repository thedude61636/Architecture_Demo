package io.d3stud.devfest.Data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.util.Log;

import java.util.List;

import io.d3stud.devfest.Data.Local.Posts.PostDatabase;
import io.d3stud.devfest.Data.Model.Post;
import io.d3stud.devfest.Data.Remote.Posts.RemotePostSource;

/**
 * Created by thedude61636 on 11/25/17.
 */

public class PostsRepo implements PostSource {

    private static final String TAG = "PostsRepo";
    private static PostsRepo INSTANCE;
    private PostSource remotePostSource;
    private PostDatabase db;

    //this is a singleton pattern look it up
    private PostsRepo(PostSource remotePostSource, PostDatabase postDatabase) {
        this.remotePostSource = remotePostSource;
        db = postDatabase;
    }

    //lazy instantiation of the singleton
    public static PostsRepo getInstance(Application application) {
        if (INSTANCE == null) {
            PostDatabase postDatabase = Room.databaseBuilder(application,
                    PostDatabase.class, "post-database").build();
            INSTANCE = new PostsRepo(new RemotePostSource(application), postDatabase);
        }
        return INSTANCE;
    }

    @Override
    public void getSinglePost(int postId, final SinglePostCallback singlePostCallback) {
        LiveData<Post> post = db.postDao().findById(postId);
        if (post.getValue() != null) {
            Log.i(TAG, "getSinglePost: from database");
            singlePostCallback.singlePostLoaded(post.getValue());
        } else {
            Log.i(TAG, "getSinglePost: from network");
            remotePostSource.getSinglePost(postId, new SinglePostCallback() {
                @Override
                public void singlePostLoaded(Post post) {
                    singlePostCallback.singlePostLoaded(post);
                }

                @Override
                public void error(String error) {
                    singlePostCallback.error(error);
                }
            });
        }
    }

    @Override
    public void getPosts(final PostsCallback postsCallback) {
        LiveData<List<Post>> postList = db.postDao().getAll();
        if (postList.getValue() != null) {
            Log.i(TAG, "getPosts: from database");
            postsCallback.postsLoaded(postList.getValue());
        } else {
            Log.i(TAG, "getPosts: from network");
            remotePostSource.getPosts(new PostsCallback() {
                @Override
                public void postsLoaded(List<Post> posts) {
                    postsCallback.postsLoaded(posts);
                }

                @Override
                public void error(String error) {
                    postsCallback.error(error);
                }
            });
        }
    }

    public void inserPosts(Post... posts) {

        db.postDao().insertAll(posts);
    }


    public void clearPosts() {
        db.postDao().nukeTable();
    }
}
