package io.d3stud.devfest.Data.Model;


import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by thedude61636 on 11/24/17.
 */
//the annotation here is for room
@Entity(primaryKeys = {"id"}, tableName = "posts")
public class Post {


    /**
     * userId : 1
     * id : 1
     * title : sunt aut facere repellat provident occaecati excepturi optio reprehenderit
     * body : quia et suscipit
     * suscipit recusandae consequuntur expedita et cum
     * reprehenderit molestiae ut ut quas totam
     * nostrum rerum est autem sunt rem eveniet architecto
     */

    @SerializedName("userId")
    private int userId;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Post{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
