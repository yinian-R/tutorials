package com.wymm.stream._2grouping;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BlogPost {
    String title;
    String author;
    BlogPostType type;
    int likes;
}
