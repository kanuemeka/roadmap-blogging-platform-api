package com.example.blog.controller;

import com.example.blog.controller.adapters.*;
import com.example.blog.domain.BlogPost;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostCreator blogPostCreator;

    private final BlogPostUpdator blogPostUpdator;

    private final BlogPostRetriever blogPostRetriever;

    private final BlogPostsRetriever blogPostsRetriever;

    private final BlogPostsSearcher blogPostsSearcher;

    private final BlogPostDeleter blogPostDeleter;

    @PostMapping("/posts")
    public ResponseEntity<BlogResponse> postBlog(@Valid @RequestBody BlogRequest blogRequest) {

        BlogPost blogPost = blogPostCreator.createBlogPost(blogRequest);
        BlogResponse blogResponse = mapBlogPostToBlogResponse(blogPost);

        return ResponseEntity.created(URI.create("/posts/1")).body(blogResponse);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<BlogResponse> updateBlog(@PathVariable("id") String id, @Valid @RequestBody BlogRequest blogRequest) {

        BlogPost updatedPost = blogPostUpdator.updateBlogPost(id, blogRequest);

        BlogResponse blogResponse = mapBlogPostToBlogResponse(updatedPost);

        return ResponseEntity.ok(blogResponse);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<BlogResponse> getBlog(@PathVariable("id") String id) {
        BlogPost blogPost = blogPostRetriever.getBlogPost(id);

        BlogResponse blogResponse = mapBlogPostToBlogResponse(blogPost);

        return ResponseEntity.ok(blogResponse);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<BlogResponse>> getAllBlogs(@RequestParam(value = "term", required = false) String searchTerm) {
        List<BlogPost> blogPosts;

        if(searchTerm!=null && !searchTerm.isEmpty()) {
            blogPosts = blogPostsSearcher.getAllBlogPosts(searchTerm);
        }else {
            blogPosts = blogPostsRetriever.getAllBlogPosts();
        }

        if(blogPosts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<BlogResponse> blogResponses = blogPosts.stream().map(BlogPostController::mapBlogPostToBlogResponse).toList();

        return ResponseEntity.ok(blogResponses);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity deleteBlog(@PathVariable("id") String id) {
        blogPostDeleter.deleteBlogPost(id);

        return ResponseEntity.ok().build();
    }

    private static BlogResponse mapBlogPostToBlogResponse(BlogPost blogPost) {
        return BlogResponse.builder()
        .id(blogPost.getId())
        .title(blogPost.getTitle())
        .content(blogPost.getContent())
        .category(blogPost.getCategory())
        .tags(blogPost.getTags())
        .createdAt(blogPost.getCreated())
        .updatedAt(blogPost.getUpdated())
        .build();
    }
}
