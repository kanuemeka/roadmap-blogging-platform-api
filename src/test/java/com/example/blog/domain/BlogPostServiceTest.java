package com.example.blog.domain;

import com.example.blog.controller.BlogRequest;
import com.example.blog.data.BlogRepository;
import com.example.blog.exceptions.BlogNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BlogPostServiceTest {

    BlogPostsService blogPostService;

    @MockBean
    BlogRepository blogRepository;

    @BeforeEach
    public void setUp() {
        blogPostService = new BlogPostsService(blogRepository);
    }

    @Test
    public void shouldPersistBlogPost() {
        String id = "randomId";
        String title = "title";
        String content = "content";
        String category = "category";
        List<String> tags = List.of("tag1", "tag2");

        BlogRequest blogRequest
                = BlogRequest.builder()
                .title(title)
                .content(content)
                .category(category)
                .tags(tags)
                .build();

        BlogPost blogPost
                = BlogPost.builder()
                .id(id)
                .title(title)
                .content(content)
                .category(category)
                .tags(tags)
                .created(new Date())
                .updated(new Date())
                .build();

        when(blogRepository.save(any(BlogPost.class))).thenReturn(blogPost);

        BlogPost actual = blogPostService.createBlogPost(blogRequest);

        assertNotNull(actual.getId());
        assertEquals(title, actual.getTitle());
        assertEquals(content, actual.getContent());
        assertEquals(category, actual.getCategory());
        assertEquals(tags, actual.getTags());
        assertNotNull(actual.getCreated());
        assertNotNull(actual.getUpdated());

    }

    @Test
    public void shouldUpdateBlogPost() {
        String id = "randomId";
        String title = "title";
        String udpatedTitle = "udpated title";
        String content = "content";
        String category = "category";
        List<String> tags = List.of("tag1", "tag2");

        BlogRequest blogRequest
                = BlogRequest.builder()
                .title(udpatedTitle)
                .content(content)
                .category(category)
                .tags(tags)
                .build();

        BlogPost blogPost
                = BlogPost.builder()
                .id(id)
                .title(title)
                .content(content)
                .category(category)
                .tags(tags)
                .created(new Date())
                .updated(new Date())
                .build();

        BlogPost updatedBlogPost
                = BlogPost.builder()
                .id(id)
                .title(udpatedTitle)
                .content(content)
                .category(category)
                .tags(tags)
                .created(new Date())
                .updated(new Date())
                .build();

        when(blogRepository.findById(any(String.class))).thenReturn(Optional.of(blogPost));
        when(blogRepository.save(any(BlogPost.class))).thenReturn(updatedBlogPost);

        BlogPost actual = blogPostService.updateBlogPost(id, blogRequest);

        assertNotNull(actual.getId());
        assertEquals(udpatedTitle, actual.getTitle());
        assertEquals(content, actual.getContent());
        assertEquals(category, actual.getCategory());
        assertEquals(tags, actual.getTags());
        assertNotNull(actual.getCreated());
        assertNotNull(actual.getUpdated());

    }

    @Test
    public void shouldNotUpdateBlogNotFound() {
        String id = "randomId";
        String udpatedTitle = "udpated title";
        String content = "content";
        String category = "category";
        List<String> tags = List.of("tag1", "tag2");

        BlogRequest blogRequest
                = BlogRequest.builder()
                .title(udpatedTitle)
                .content(content)
                .category(category)
                .tags(tags)
                .build();

        when(blogRepository.findById(any(String.class))).thenReturn(Optional.empty());

        assertThrows(BlogNotFoundException.class, () -> {
            blogPostService.updateBlogPost(id, blogRequest);
        });

    }

    @Test
    public void shouldGetBlogPost(){
        String id = "randomId";
        String title = "title";
        String content = "content";
        String category = "category";
        List<String> tags = List.of("tag1", "tag2");

        BlogPost blogPost
                = BlogPost.builder()
                .id(id)
                .title(title)
                .content(content)
                .category(category)
                .tags(tags)
                .created(new Date())
                .updated(new Date())
                .build();

        when(blogRepository.findById(any(String.class))).thenReturn(Optional.of(blogPost));

        BlogPost actual = blogPostService.getBlogPost(id);

        assertNotNull(actual.getId());
        assertEquals(title, actual.getTitle());
        assertEquals(content, actual.getContent());
        assertEquals(category, actual.getCategory());
        assertEquals(tags, actual.getTags());
        assertNotNull(actual.getCreated());
        assertNotNull(actual.getUpdated());
    }

    @Test
    public void shouldNotGetBlogNotFound() {
        String id = "randomId";

        when(blogRepository.findById(any(String.class))).thenReturn(Optional.empty());

        assertThrows(BlogNotFoundException.class, () -> {
            blogPostService.getBlogPost(id);
        });

    }

    @Test
    public void shouldGetAllBlogPosts(){
        List<String> tags = List.of("tag1", "tag2");
        BlogPost blogPost1
                = BlogPost.builder()
                .id("1")
                .title("title")
                .content("content")
                .category("category")
                .tags(tags)
                .created(new Date())
                .updated(new Date())
                .build();

        BlogPost blogPost2
                = BlogPost.builder()
                .id("2")
                .title("title2")
                .content("content2")
                .category("category2")
                .tags(tags)
                .created(new Date())
                .updated(new Date())
                .build();

        when(blogRepository.findAll()).thenReturn(Arrays.asList(blogPost1, blogPost2));

        List<BlogPost> blogPosts = blogPostService.getAllBlogPosts();

        assertNotNull(blogPosts);
        assertEquals(2, blogPosts.size());

    }

    @Test
    public void shouldFindNoBlogPostsAndReturnEmptyList(){

        when(blogRepository.findAll()).thenReturn(null);

        List<BlogPost> blogPosts = blogPostService.getAllBlogPosts();

        assertNotNull(blogPosts);
        assertEquals(0, blogPosts.size());

    }

    @Test
    public void shouldDeleteBlogPost(){
        String id = "randomId";
        String title = "title";
        String content = "content";
        String category = "category";
        List<String> tags = List.of("tag1", "tag2");

        BlogPost blogPost
                = BlogPost.builder()
                .id(id)
                .title(title)
                .content(content)
                .category(category)
                .tags(tags)
                .created(new Date())
                .updated(new Date())
                .build();

        when(blogRepository.findById(any(String.class))).thenReturn(Optional.of(blogPost));
        doNothing().when(blogRepository).deleteById(blogPost.getId());

        blogPostService.deleteBlogPost(id);
    }

    @Test
    public void shouldFailDeleteBlogPost(){
        String id = "randomId";

        when(blogRepository.findById(any(String.class))).thenReturn(Optional.empty());

        assertThrows(BlogNotFoundException.class, () -> {
            blogPostService.deleteBlogPost(id);
        });
    }
}
