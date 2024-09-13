package com.example.blog.domain;

import com.example.blog.controller.BlogRequest;
import com.example.blog.controller.adapters.*;
import com.example.blog.data.BlogRepository;
import com.example.blog.exceptions.BlogNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BlogPostsService implements BlogPostCreator, BlogPostUpdator, BlogPostRetriever, BlogPostsRetriever, BlogPostsSearcher {

    private final BlogRepository blogRepository;

    public BlogPost createBlogPost(BlogRequest blogRequest) {
        BlogPost blogPost = BlogPost.builder()
                .title(blogRequest.getTitle())
                .content(blogRequest.getContent())
                .category(blogRequest.getCategory())
                .tags(blogRequest.getTags())
                .created(new Date())
                .updated(new Date())
                .build();

        return blogRepository.save(blogPost);
    }

    public BlogPost updateBlogPost(String id, BlogRequest blogRequest) {
        BlogPost dbBlog = blogRepository.findById(id).orElseThrow(BlogNotFoundException::new);

        dbBlog.setTitle(blogRequest.getTitle());
        dbBlog.setContent(blogRequest.getContent());
        dbBlog.setCategory(blogRequest.getCategory());
        dbBlog.setTags(blogRequest.getTags());
        dbBlog.setUpdated(new Date());

        return blogRepository.save(dbBlog);
    }

    @Override
    public BlogPost getBlogPost(String id) {
        return blogRepository.findById(id).orElseThrow(BlogNotFoundException::new);
    }

    public List<BlogPost> getAllBlogPosts() {
        List<BlogPost> blogPosts = new ArrayList<>();
        Iterable<BlogPost> posts = blogRepository.findAll();
        if(posts!=null) {
            posts.forEach(blogPosts::add);
        }
        return blogPosts;
    }

    public List<BlogPost> getAllBlogPosts(String searchTerm) {
        List<BlogPost> blogPosts = new ArrayList<>();
        Iterable<BlogPost> posts = blogRepository.findAllByTitleContentOrCategory(searchTerm);
        if(posts!=null) {
            posts.forEach(blogPosts::add);
        }
        return blogPosts;

    }
}
