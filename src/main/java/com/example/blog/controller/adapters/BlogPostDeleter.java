package com.example.blog.controller.adapters;


@FunctionalInterface
public interface BlogPostDeleter {

    void deleteBlogPost(String id);
}
