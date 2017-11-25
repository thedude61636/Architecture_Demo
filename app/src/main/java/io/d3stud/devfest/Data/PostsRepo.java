package io.d3stud.devfest.Data;

import android.app.Application;

import io.d3stud.devfest.Data.Remote.Posts.RemotePostSource;

/**
 * Created by thedude61636 on 11/25/17.
 */

public class PostsRepo implements PostSource {

    private static PostsRepo INSTANCE;
    private PostSource remotePostSource;

    //this is a singleton pattern look it up
    private PostsRepo(PostSource remotePostSource) {
        this.remotePostSource = remotePostSource;
    }

    //lazy instantiation of the singleton
    public static PostsRepo getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new PostsRepo(new RemotePostSource(application));
        }
        return INSTANCE;
    }

    //currently just passing the callback because we aren't using any caching but we'll be adding offline caching next 
    @Override
    public void getSinglePost(int postId, SinglePostCallback singlePostCallback) {
        remotePostSource.getSinglePost(postId, singlePostCallback);
    }

    @Override
    public void getPosts(PostsCallback postsCallback) {
        remotePostSource.getPosts(postsCallback);
    }
}
