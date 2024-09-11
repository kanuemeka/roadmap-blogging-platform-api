package com.example.blog.controller;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostBlogRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String category;

    @NotNull
    private List<String> tags;
}
