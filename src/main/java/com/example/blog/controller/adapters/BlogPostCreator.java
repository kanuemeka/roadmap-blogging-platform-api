package com.example.blog.controller.adapters;

import com.example.blog.controller.BlogRequest;
import com.example.blog.domain.BlogPost;

@FunctionalInterface
public interface BlogPostCreator {

    BlogPost createBlogPost(BlogRequest blogRequest);
}
