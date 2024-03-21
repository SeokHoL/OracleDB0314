package kr.ac.kopo.oracledb0314.repository;

import jakarta.transaction.Transactional;
import kr.ac.kopo.oracledb0314.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.beans.Transient;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());


    }

    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1, 100).forEach(i ->{
            Memo memo = Memo.builder().memoText("Dummy Data Test" + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect() {
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("===========================");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Transactional
    @Test
    public void testSelect2() {
        Long mno = 100L;

        Memo memo= memoRepository.getOne(mno);

        System.out.println("===========================");

        System.out.println(memo);

    }

    @Test
    public void testUpdate(){
        Memo memo = Memo.builder().mno(95L).memoText("=Update Dummy Date 95").build();

        Memo memo1=memoRepository.save(memo);

        System.out.println(memo1);
    }

    @Test
    public void testDelete(){

        Long mno =
    }


    }

