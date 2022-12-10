package com.sparta.hanghaememo.entity;
import com.sparta.hanghaememo.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "MEMO")
public class Memo extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "memo") //테이블 이름으로 mappedBy
    @OrderBy("createdAt desc")
    private List<Comment> commentList= new ArrayList<>();

    @OneToMany(mappedBy = "memo")
    private List<MemoLike> memoLikes=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;


    public Memo(MemoRequestDto requestDto , Users users) {

        this.username = users.getUsername();
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
        this.users = users;

    }
    public void update(MemoRequestDto memoRequestDto) {

        this.title = memoRequestDto.getTitle();
        this.username = memoRequestDto.getUsername();
        this.contents = memoRequestDto.getContents();

    }



}