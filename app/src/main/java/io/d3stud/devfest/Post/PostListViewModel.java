package io.d3stud.devfest.Post;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.d3stud.devfest.Data.Model.Post;
import io.d3stud.devfest.Data.PostsApi;
import io.d3stud.devfest.Data.RequestBuilder;
import io.d3stud.devfest.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thedude61636 on 11/25/17.
 */
//this is a view model for the post list activity it's auto-magically managed by the android framework
// and is constructed when you first obtain it and it persists throughout the activity lifecycle
public class PostListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Post>> posts = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    //it's constructor
    public PostListViewModel(@NonNull Application application) {
        super(application);
        loadPosts();
    }

    // for now we just load the values and store it in a list
    void loadPosts() {
        isLoading.setValue(true);
        final PostsApi postsApi =
                RequestBuilder.init().build().create(PostsApi.class);

        postsApi.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                isLoading.setValue(false);
                if (response.body() != null) {

                    posts.setValue(response.body());

                } else {
                    error.setValue(getApplication().getString(R.string.error));
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                isLoading.setValue(false);
                error.setValue(t.getLocalizedMessage());
            }
        });

    }

    //this returns a non mutable live data object that we will subscribe to
    public LiveData<List<Post>> getPosts() {
        return posts;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }
}

