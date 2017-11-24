package io.d3stud.devfest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by thedude61636 on 11/24/17.
 */

//this should be obvious if you know retrofit
public interface PostsApi {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{id}")
    Call<Post> getSinglePost(@Path("id") int postId);
}
