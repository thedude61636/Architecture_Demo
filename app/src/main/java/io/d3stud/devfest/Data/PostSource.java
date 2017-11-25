package io.d3stud.devfest.Data;

import java.util.List;

import io.d3stud.devfest.Data.Model.Post;

/**
 * Created by thedude61636 on 11/25/17.
 */

public interface PostSource {

    //this interface is used later to load data from the repo and from the remote source and the local source
    void getSinglePost(int postId, SinglePostCallback singlePostCallback);

    void getPosts(PostsCallback postsCallback);

    interface SinglePostCallback {
        void singlePostLoaded(Post post);

        void error(String error);
    }

    interface PostsCallback {
        void postsLoaded(List<Post> posts);

        void error(String error);
    }
}
