package com.example.blog.controller.adapters;

import com.example.blog.domain.BlogPost;

import java.util.List;

@FunctionalInterface
public interface BlogPostsRetriever {

    List<BlogPost> getAllBlogPosts();
}
