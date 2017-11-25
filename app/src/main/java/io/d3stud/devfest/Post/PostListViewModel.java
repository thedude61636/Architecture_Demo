package io.d3stud.devfest.Post;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.d3stud.devfest.Data.Model.Post;
import io.d3stud.devfest.Data.PostSource;
import io.d3stud.devfest.Data.PostsRepo;

/**
 * Created by thedude61636 on 11/25/17.
 */
//this is a view model for the post list activity it's auto-magically managed by the android framework
// and is constructed when you first obtain it and it persists throughout the activity lifecycle
public class PostListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Post>> postsLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    private PostsRepo postsRepo;

    //it's constructor
    public PostListViewModel(@NonNull Application application) {
        super(application);
        postsRepo = PostsRepo.getInstance(application);
        loadPosts();
    }

    // for now we just load the values and store it in a list
    void loadPosts() {
        isLoading.setValue(true);

        //we changed the ugly code to some nice code
        postsRepo.getPosts(new PostSource.PostsCallback() {
            @Override
            public void postsLoaded(List<Post> posts) {
                isLoading.setValue(false);
                postsLiveData.setValue(posts);

            }

            @Override
            public void error(String error) {
                isLoading.setValue(false);
                errorLiveData.setValue(error);
            }
        });

    }

    //this returns a non mutable live data object that we will subscribe to
    public LiveData<List<Post>> getPosts() {
        return postsLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }
}

