package com.example.blog.controller;

import com.example.blog.domain.BlogPost;
import com.example.blog.domain.BlogPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
    private BlogPostService blogPostService;

    @Test
    public void shouldPostBlogSuccessfully() throws Exception {
        PostBlogRequest postBlogRequest
                = PostBlogRequest.builder()
                .title("title")
                .content("content")
                .category("category")
                .tags(List.of("tag1", "tag2"))
                .build();

        BlogPost blogPost
                = BlogPost.builder()
                .id(1L)
                .title("title")
                .content("content")
                .category("category")
                .tags(List.of("tag1", "tag2"))
                .build();

        when(blogPostService.createBlogPost(postBlogRequest)).thenReturn(blogPost);

        PostBlogResponse expectedBlogResponse
                = PostBlogResponse.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .content(blogPost.getContent())
                .category(blogPost.getCategory())
                .tags(blogPost.getTags())
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/posts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postBlogRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBlogResponse)));

    }
}
