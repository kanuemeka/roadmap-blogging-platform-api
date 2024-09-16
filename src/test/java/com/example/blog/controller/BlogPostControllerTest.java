package com.example.blog.controller;

import com.example.blog.controller.adapters.*;
import com.example.blog.domain.BlogPost;
import com.example.blog.exceptions.BlogNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BlogPostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BlogPostCreator blogPostCreator;

    @MockBean
    private BlogPostUpdator blogPostUpdator;

    @MockBean
    private BlogPostRetriever blogPostRetriever;

    @MockBean
    private BlogPostsRetriever blogPostsRetriever;

    @MockBean
    private BlogPostsSearcher blogPostsSearcher;

    @MockBean
    private BlogPostDeleter blogPostDeleter;

    @Test
    public void shouldPostBlogSuccessfully() throws Exception {
        Date createdDate = new Date();
        Date updatedDate = new Date();

        BlogRequest blogRequest
                = BlogRequest.builder()
                .title("title")
                .content("content")
                .category("category")
                .tags(List.of("tag1", "tag2"))
                .build();

        BlogPost blogPost
                = BlogPost.builder()
                .id("1L")
                .title("title")
                .content("content")
                .category("category")
                .tags(List.of("tag1", "tag2"))
                .created(createdDate)
                .updated(updatedDate)
                .build();

        when(blogPostCreator.createBlogPost(blogRequest)).thenReturn(blogPost);

        BlogResponse expectedBlogResponse
                = BlogResponse.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .content(blogPost.getContent())
                .category(blogPost.getCategory())
                .tags(blogPost.getTags())
                .createdAt(createdDate)
                .updatedAt(updatedDate)
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/posts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blogRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBlogResponse)));

    }

    @Test
    public void shouldFailRequestValidation() throws Exception {
        BlogRequest blogRequest
                = BlogRequest.builder()
                .title("title")
                .content("content")
                .tags(List.of("tag1", "tag2"))
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/posts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blogRequest)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void shouldUpdateBlogSuccessfully() throws Exception {
        String id = "1L";

        BlogRequest blogRequest
                = BlogRequest.builder()
                .title("new title")
                .content("content")
                .category("category")
                .tags(List.of("tag1", "tag2"))
                .build();

        BlogPost blogPost
                = BlogPost.builder()
                .id(id)
                .title("new title")
                .content("content")
                .category("category")
                .tags(List.of("tag1", "tag2"))
                .build();

        when(blogPostUpdator.updateBlogPost(id, blogRequest)).thenReturn(blogPost);

        BlogResponse expectedBlogResponse
                = BlogResponse.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .content(blogPost.getContent())
                .category(blogPost.getCategory())
                .tags(blogPost.getTags())
                .build();

        mvc.perform(MockMvcRequestBuilders.put("/posts/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blogRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBlogResponse)));

    }

    @Test
    public void shouldFailBlogNotFound() throws Exception {
        String id = "1L";

        BlogRequest blogRequest
                = BlogRequest.builder()
                .title("new title")
                .content("content")
                .category("category")
                .tags(List.of("tag1", "tag2"))
                .build();

        when(blogPostUpdator.updateBlogPost(id, blogRequest)).thenThrow(BlogNotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.put("/posts/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blogRequest)))
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldGetBlogSuccessfully() throws Exception {
        String id = "1L";
        Date createdDate = new Date();
        Date updatedDate = new Date();

        BlogPost blogPost
                = BlogPost.builder()
                .id(id)
                .title("title")
                .content("content")
                .category("category")
                .tags(List.of("tag1", "tag2"))
                .created(createdDate)
                .updated(updatedDate)
                .build();

        BlogResponse expectedBlogResponse
                = BlogResponse.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .content(blogPost.getContent())
                .category(blogPost.getCategory())
                .tags(blogPost.getTags())
                .createdAt(createdDate)
                .updatedAt(updatedDate)
                .build();

        when(blogPostRetriever.getBlogPost(id)).thenReturn(blogPost);

        mvc.perform(MockMvcRequestBuilders.get("/posts/{id}", id)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBlogResponse)));
    }

    @Test
    public void shouldFailGetBlogNotFound() throws Exception {
        String id = "1L";

        BlogRequest blogRequest
                = BlogRequest.builder()
                .title("new title")
                .content("content")
                .category("category")
                .tags(List.of("tag1", "tag2"))
                .build();

        when(blogPostRetriever.getBlogPost(id)).thenThrow(BlogNotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.get("/posts/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blogRequest)))
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldGetAllBlogsSuccessfully() throws Exception {
        List<String> tags = List.of("tag1", "tag2");
        Date createdDate = new Date();
        Date updatedDate = new Date();

        BlogPost blogPost1 = BlogPost.builder()
                .id("1")
                .title("title")
                .content("content")
                .category("category")
                .tags(tags)
                .created(createdDate)
                .updated(updatedDate)
                .build();

        BlogPost blogPost2
                = BlogPost.builder()
                .id("2")
                .title("title2")
                .content("content2")
                .category("category2")
                .tags(tags)
                .created(createdDate)
                .updated(updatedDate)
                .build();

        when(blogPostsRetriever.getAllBlogPosts()).thenReturn(List.of(blogPost1, blogPost2));

        mvc.perform(MockMvcRequestBuilders.get("/posts")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(expectedBlogResponse)))
        ;
    }

    @Test
    public void shouldGetEmptyBlogsNoContent() throws Exception {

        when(blogPostsRetriever.getAllBlogPosts()).thenReturn(List.of());

        mvc.perform(MockMvcRequestBuilders.get("/posts")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteBlogSuccessfully() throws Exception {
        String id = "1L";

    }
}
