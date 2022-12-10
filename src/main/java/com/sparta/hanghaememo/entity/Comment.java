package com.sparta.hanghaememo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.hanghaememo.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "COMMENT")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)//로딩
    @JoinColumn(name = "MEMO_ID")
    @JsonIgnore
    private Memo memo;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String username;

    @OneToMany(mappedBy = "comment")
    private List<CommentLike> commentLikes = new ArrayList<>();



    public void update(CommentDto commentdto){
        this.comment = commentdto.getComment();

    }


}
