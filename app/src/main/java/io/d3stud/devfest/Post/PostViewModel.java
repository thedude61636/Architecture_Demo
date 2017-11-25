package io.d3stud.devfest.Post;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import io.d3stud.devfest.Data.Model.Post;
import io.d3stud.devfest.Data.Remote.Posts.PostsApi;
import io.d3stud.devfest.Data.RequestBuilder;
import io.d3stud.devfest.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thedude61636 on 11/25/17.
 */

public class PostViewModel extends AndroidViewModel {

    private MutableLiveData<Post> postLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private int postId;
    //just a constructor, but we can't load the post because we don't its id
    public PostViewModel(@NonNull Application application) {
        super(application);
    }


    //this just loads data and stores it in the postLiveData
    void loadPost(final int postId) {
        this.postId = postId;
        if (postLiveData.getValue() == null) {
            isLoading.setValue(true);
            PostsApi postsApi = RequestBuilder.init().build().create(PostsApi.class);
            postsApi.getSinglePost(postId).enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    isLoading.setValue(false);
                    if (response.body() != null) {
                        postLiveData.setValue(response.body());
                    } else {
                        error.setValue(getApplication().getString(R.string.error));
                    }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    isLoading.setValue(false);
                    error.setValue(t.getLocalizedMessage());

                }
            });
        }
    }

    //    this refreshes the data
    void refreshPost() {
        postLiveData.setValue(null);
        loadPost(postId);
    }

    // Always return a non mutable live data object
    public LiveData<Post> getPost() {
        return postLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }
}
