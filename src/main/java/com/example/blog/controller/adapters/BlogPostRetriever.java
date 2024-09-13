package com.example.blog.controller.adapters;

import com.example.blog.domain.BlogPost;


@FunctionalInterface
public interface BlogPostRetriever {

    BlogPost getBlogPost(String id);
}
