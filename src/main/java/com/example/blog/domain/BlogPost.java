package com.example.blog.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class BlogPost {

    private Long id;

    private String title;

    private String content;

    private String category;

    private List<String> tags;

    private Date created;

    private Date updated;
}
