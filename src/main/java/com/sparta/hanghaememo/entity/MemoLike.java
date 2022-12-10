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
    private Memo memo;

}
