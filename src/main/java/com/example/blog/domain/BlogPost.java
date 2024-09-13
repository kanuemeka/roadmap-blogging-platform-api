package com.example.blog.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class BlogPost {

    @Id
    private String id;

    private String title;

    private String content;

    private String category;

    private List<String> tags;

    private Date created;

    private Date updated;
}
