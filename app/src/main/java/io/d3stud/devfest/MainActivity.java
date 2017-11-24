package io.d3stud.devfest;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;

import io.d3stud.devfest.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;
    PostsAdapter postsAdapter = new PostsAdapter();
    LovelyStandardDialog errorDialog;

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

        //this monitors the lifecycle changes
        getLifecycle().addObserver(new GenericLifecycleObserver() {
            @Override
            public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
                if (event.equals(Lifecycle.Event.ON_PAUSE)) {
                    dismissDialog();
                }
                Log.d(TAG, "onStateChanged() called with: source = [" + source + "], event = [" + event + "]");
            }
        });

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

    //this dismisses the dialog before rotating
    private void dismissDialog() {
        Log.d(TAG, "dismissDialog() called");
        if (errorDialog != null && errorDialog.isShowing())
            errorDialog.dismiss();
    }

    private void showErrorDialog(String error) {
        Log.d(TAG, "showErrorDialog() called with: error = [" + error + "]");
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
            Log.d(TAG, "inside the if");
            // this crashes on some devices for some reason
            // and it might crash in the background for some reason
            // and we can't test this in the emulator
            // so we'll just pretend that it's crashing

//            Post post = null;
//            post.getBody();

            //shows a dialog
            errorDialog = new LovelyStandardDialog(this)
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
                    });

            errorDialog.show();
        }
    }

}
