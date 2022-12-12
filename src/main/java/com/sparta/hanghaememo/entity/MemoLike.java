package com.sparta.hanghaememo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "MEMO_LIKE")
public class MemoLike {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long mLikeID;

    @ManyToOne
    @JoinColumn(name = "MEMO_ID")
    private Memo memo;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public MemoLike(User user, Memo memo){
        this.user = user;
        this.memo = memo;
    }



}
