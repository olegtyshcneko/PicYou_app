package com.olegtyshchenko.PicYouAPI.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Image {
    public String updated_at;
    public String author_avatar_url;
    public Size small;
    public Boolean is_liked;
    public int comments_count;
    public int width;
    public String user_id;
    public Size thumb;
    public int height;
    public Boolean frame_applied;
    public Boolean is_promoted;
    public String id;
    public int likes_count;
    public List<Like> likes;
    public String author_name;
    public Medium medium;
    public String url;
    public String applied_filter;
    public String title;
    public String created_at;
    @SerializedName("author_pro_user?")
    public Boolean author_pro_user;
    public List<Comment> comments;
    public Boolean full;
}
