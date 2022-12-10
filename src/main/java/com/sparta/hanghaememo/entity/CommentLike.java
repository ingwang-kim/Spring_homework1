package com.sparta.hanghaememo.entity;

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

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Users users;


    public CommentLike( Users users , Comment comment) {
        this.users = users;
        this.comment=comment;
    }
}
