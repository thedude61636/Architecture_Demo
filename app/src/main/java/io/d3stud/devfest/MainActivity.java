package io.d3stud.devfest;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;

import io.d3stud.devfest.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    PostsAdapter postsAdapter = new PostsAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //using databinding to set the views
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        //setting up the recycler and the refresh view
        binding.postRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.postRecycler.setAdapter(postsAdapter);
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postsAdapter.clear();
                getPosts();
            }
        });

        //getting the posts
        getPosts();

    }

    //loads posts from the api
    void getPosts() {
        binding.refreshLayout.setRefreshing(true);
        binding.statusImg.setVisibility(View.GONE);
        PostsApi postsApi =
                RequestBuilder.init().build().create(PostsApi.class);

        postsApi.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                binding.refreshLayout.setRefreshing(false);
                if (response.body() != null)
                    postsAdapter.changeItems(response.body());
                else {
                    showErrorDialog(getString(R.string.error));
                    showStatusImg(getString(R.string.error));
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                binding.refreshLayout.setRefreshing(false);
                showErrorDialog(t.getLocalizedMessage());
                showStatusImg(t.getLocalizedMessage());
            }
        });
    }

    //show a little image with status text
    private void showStatusImg(String error) {
        binding.statusImg.setText(error);
        binding.statusImg.setVisibility(View.GONE);
    }

    private void showErrorDialog(String error) {

        // this crashes on some devices for some reason
        // and it might crash in the background for some reason
        // and we can't test this in the emulator
        // so we'll just pretend that it's crashing
//        Post post = null;
//        post.getBody();

        //shows a dialog
        new LovelyStandardDialog(this)
                .setButtonsColorRes(R.color.colorAccent)
                .setTopColorRes(R.color.error)
                .setTopTitle(R.string.error_title)
                .setIcon(R.drawable.ic_neutral)
                .setTopTitleColor(Color.WHITE)
                .setMessage(error)
                .setPositiveButton(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getPosts();
                    }
                })
                .setNegativeButton(R.string.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //dialog dismisses itself
                    }
                }).show();
    }

}
