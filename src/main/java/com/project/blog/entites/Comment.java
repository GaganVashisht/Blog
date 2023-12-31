package com.project.blog.entites;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="comments")
@Getter
@Setter
@NoArgsConstructor
public class  Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;
    @ManyToOne
    private User user;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
