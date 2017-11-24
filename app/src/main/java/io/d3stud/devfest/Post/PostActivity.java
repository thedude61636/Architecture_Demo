package io.d3stud.devfest.Post;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import io.d3stud.devfest.Consts;
import io.d3stud.devfest.R;
import io.d3stud.devfest.databinding.ActivityPostBinding;

public class PostActivity extends AppCompatActivity {
    private static final String TAG = "PostActivity";
    ActivityPostBinding binding;
    int postId;
    private PostViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //databinding again
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        setSupportActionBar(binding.toolbar);
        //this shows the back button and in the manifest you can add a value that makes this arrow opens another arrow when clicked

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //creating the view model you should always create it first
        viewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        //making the refresh button work
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // this is for showing the data when we get it from the network
                Log.i(TAG, "onClick: " + viewModel.getPost().toString());
            }
        });

        //getting the value passed by the adapter
        postId = getIntent().getIntExtra(Consts.POST_ID, -1);
        //getting the post when you start the activity
        viewModel.loadPost(postId);
    }


    //showing a toast when there's an error
    private void showError(String error) {
        Snackbar.make(binding.getRoot(), error, Snackbar.LENGTH_LONG).show();
    }
}
