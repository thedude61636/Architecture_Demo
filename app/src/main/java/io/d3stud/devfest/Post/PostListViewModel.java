package io.d3stud.devfest.Post;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import io.d3stud.devfest.Data.Model.Post;
import io.d3stud.devfest.Data.PostsApi;
import io.d3stud.devfest.Data.RequestBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thedude61636 on 11/25/17.
 */
//this is a view model for the post list activity it's auto-magically managed by the android framework
// and is constructed when you first obtain it and it persists throughout the activity lifecycle
public class PostListViewModel extends ViewModel {

    List<Post> posts = new ArrayList<>();

    //it's constructor
    public PostListViewModel() {
        loadPosts();
    }

    // a simple getter DON'T DO THIS use LiveData
    public List<Post> getPosts() {
        return posts;
    }

    // for now we just load the values and store it in a list
    void loadPosts() {
//        binding.refreshLayout.setRefreshing(true);
//        binding.statusImg.setVisibility(View.GONE);
        PostsApi postsApi =
                RequestBuilder.init().build().create(PostsApi.class);

        postsApi.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                binding.refreshLayout.setRefreshing(false);
                if (response.body() != null) {
                    posts = response.body();

//                    postsAdapter.changeItems(response.body());
                } else {
//                    showErrorDialog(getString(R.string.error));
//                    showStatusImg(getString(R.string.error));
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
//                binding.refreshLayout.setRefreshing(false);
//                showErrorDialog(t.getLocalizedMessage());
//                showStatusImg(t.getLocalizedMessage());
            }
        });

    }

}

