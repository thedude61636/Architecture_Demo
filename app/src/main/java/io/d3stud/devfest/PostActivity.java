package io.d3stud.devfest;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.d3stud.devfest.databinding.ActivityPostBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {
    ActivityPostBinding binding;
    int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //databinding again
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        setSupportActionBar(binding.toolbar);
        //this shows the back button and in the manifest you can add a value that makes this arrow opens another arrow when clicked

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //making the refresh button work
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSinglePost(postId);
            }
        });

        //getting the value passed by the adapter
        postId = getIntent().getIntExtra(Consts.POST_ID, -1);
        //getting the post when you start the activity
        getSinglePost(postId);
    }

    //another retrofit call
    void getSinglePost(int postId) {
        binding.setPost(null);
        binding.progress.setVisibility(View.VISIBLE);
        PostsApi postsApi = RequestBuilder.init().build().create(PostsApi.class);
        postsApi.getSinglePost(postId).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                binding.progress.setVisibility(View.GONE);
                if (response.body() != null)
                    binding.setPost(response.body());
                else {
                    showError(getString(R.string.error));
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                binding.progress.setVisibility(View.GONE);
                showError(t.getLocalizedMessage());

            }
        });

    }

    //showing a toast when there's an error
    private void showError(String error) {
        Snackbar.make(binding.getRoot(), error, Snackbar.LENGTH_LONG).show();
    }
}
