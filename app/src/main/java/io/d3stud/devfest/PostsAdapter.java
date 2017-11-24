package io.d3stud.devfest;

/**
 * Created by thedude61636 on 11/24/17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.d3stud.devfest.databinding.PostItemBinding;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    Context context;
    List<Post> postList;


    public PostsAdapter() {

        this.postList = postList = new ArrayList<>();
    }

    //this adds items
    public void addItems(List<Post> postList) {
        this.postList.addAll(postList);
        notifyItemRangeInserted(this.postList.size(), postList.size());
    }

    //this changes the items
    public void changeItems(List<Post> postList) {
        this.postList = postList;
        notifyDataSetChanged();
    }

    //this removes the items
    public void clear() {
        postList.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //again using databinding
        PostItemBinding binding = PostItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //setting the value
        holder.setPost(postList.get(position));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        PostItemBinding binding;

        ViewHolder(PostItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        //setting the item in the view
        void setPost(Post post) {
            binding.setPost(post);
        }


        @Override
        public void onClick(View view) {
            //this just opens the next activity
            Intent intent = new Intent(binding.getRoot().getContext(), PostActivity.class);
            intent.putExtra(Consts.POST_ID, postList.get(getAdapterPosition()).getId());
            binding.getRoot().getContext().startActivity(intent);
        }
    }
}