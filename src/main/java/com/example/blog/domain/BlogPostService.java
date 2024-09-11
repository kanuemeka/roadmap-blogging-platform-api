package com.example.blog.domain;

import com.example.blog.controller.PostBlogRequest;
import org.springframework.stereotype.Component;

@Component
public class BlogPostService {

    public BlogPost createBlogPost(PostBlogRequest postBlogRequest) {
        BlogPost blogPost = BlogPost.builder().build();

        return blogPost;
    }


}
