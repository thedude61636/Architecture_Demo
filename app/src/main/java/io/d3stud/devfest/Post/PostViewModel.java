package io.d3stud.devfest.Post;

import android.arch.lifecycle.ViewModel;

import io.d3stud.devfest.Data.Model.Post;
import io.d3stud.devfest.Data.PostsApi;
import io.d3stud.devfest.Data.RequestBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thedude61636 on 11/25/17.
 */

public class PostViewModel extends ViewModel {

    Post post = null;

    //just a constructor, but we can't load the post because we don't its id
    public PostViewModel() {

    }

    //this just loads data and stores it in the post var
    void loadPost(final int postId) {

        if (post == null) {
            PostsApi postsApi = RequestBuilder.init().build().create(PostsApi.class);
            postsApi.getSinglePost(postId).enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
//                binding.progress.setVisibility(View.GONE);
                    if (response.body() != null) {
                        post = response.body();
//                    binding.setPost(response.body());
                    } else {
//                    showError(getString(R.string.error));
                    }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
//                binding.progress.setVisibility(View.GONE);
//                showError(t.getLocalizedMessage());

                }
            });
        }
    }

    // a normal getter
    public Post getPost() {
        return post;
    }
}
