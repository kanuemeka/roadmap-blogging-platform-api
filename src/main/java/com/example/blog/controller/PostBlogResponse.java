package com.example.blog.controller;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class PostBlogResponse {

    private Long id;

    private String title;

    private String content;

    private String category;

    private List<String> tags;

    private Date createdAt;

    private Date updatedAt;
}
