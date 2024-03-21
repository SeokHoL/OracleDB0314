package kr.ac.kopo.oracledb0314.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity //개체
@Table(name ="tbl_memo") //테이블이름 지정
@ToString // 보여주는 문자열
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Memo {
    @Id // 기본키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //1씩 자동으로 증가
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;



}
