package com.project.blog.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    String content;
    Integer userId;
    Integer postId;
}
