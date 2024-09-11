package com.example.blog.controller;

import com.example.blog.domain.BlogPost;
import com.example.blog.domain.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostService blogPostService;

    @PostMapping("/posts")
    public ResponseEntity<PostBlogResponse> postBlog(@RequestBody PostBlogRequest postBlogRequest) {

        BlogPost blogPost = blogPostService.createBlogPost(postBlogRequest);
        PostBlogResponse postBlogResponse
                = PostBlogResponse.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .content(blogPost.getContent())
                .category(blogPost.getCategory())
                .tags(blogPost.getTags())
                .build();

        return ResponseEntity.created(URI.create("/posts/1")).body(postBlogResponse);
    }
}
