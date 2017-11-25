package io.d3stud.devfest.Data.Remote.Posts;

import android.app.Application;
import android.content.Context;

import java.util.List;

import io.d3stud.devfest.Data.Model.Post;
import io.d3stud.devfest.Data.PostSource;
import io.d3stud.devfest.Data.RequestBuilder;
import io.d3stud.devfest.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thedude61636 on 11/25/17.
 */

public class RemotePostSource implements PostSource {
    private Context context;
    private PostsApi postsApi = RequestBuilder.init().build().create(PostsApi.class);

    public RemotePostSource(Application application) {
        this.context = application;
    }

    @Override
    public void getSinglePost(int postId, final SinglePostCallback singlePostCallback) {
        postsApi.getSinglePost(postId).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.body() != null) {
                    singlePostCallback.singlePostLoaded(response.body());
                } else {
                    singlePostCallback.error(context.getString(R.string.error));
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                singlePostCallback.error(t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void getPosts(final PostsCallback postsCallback) {

        postsApi.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.body() != null) {
                    postsCallback.postsLoaded(response.body());

                } else {
                    postsCallback.error(context.getString(R.string.error));
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                postsCallback.error(t.getLocalizedMessage());
            }
        });
    }
}
