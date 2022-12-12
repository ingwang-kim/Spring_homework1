package com.sparta.hanghaememo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMMENT_LIKE")
public class CommentLike {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;



    public CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }
}
