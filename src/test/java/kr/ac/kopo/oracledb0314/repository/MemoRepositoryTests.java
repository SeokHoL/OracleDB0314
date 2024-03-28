package kr.ac.kopo.oracledb0314.repository;

import jakarta.transaction.Transactional;
import kr.ac.kopo.oracledb0314.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;


import java.beans.Transient;
import java.util.List;
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
    // MemoRepository의 save(Memo Entity 객체의 참조값)를 호출해서 insert한다.
    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1, 100).forEach(i ->{
            Memo memo = Memo.builder().memoText("Dummy Data Test" + i).build();
            memoRepository.save(memo);
        });
    }

    //MemoRepository의 findById(Memo Entity의 객체의 id로 설정된 필드값)를 호출해서 select한다.
    //findById() 호출되면 바로 select문을 실행한다.
    //findById()는 NULLPointerException이 발생되지 않도록 NULL 체크를 한다.


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

    //MemoRepository의 getOne(Memo Entity의 객체의 id로 설정된 필드값)를 호출해서 select한다.
    //getOne() 호출되면 바로 실행되지 않고 Memo Entity가 필요할 때 select를 실행한다.
    @Transactional
    @Test
    public void testSelect2() {
        Long mno = 100L;

        Memo memo= memoRepository.getOne(mno);

        System.out.println("===========================");

        System.out.println(memo);

    }

    // MemoRepository의 save(Memo Entity 객체의 참조값)를 호출해서 update한다.
    // 이게 가능한 이유는 save()는 호출되면 먼저 select를 하기 때문에 기존에 Entity가 있을때는 update를 실행한다.
    @Test
    public void testUpdate(){
        Memo memo = Memo.builder().mno(95L).memoText("=Update Dummy Date 95").build();

        Memo memo1=memoRepository.save(memo);

        System.out.println(memo1);
    }


    // MemoRepository의 deleteById(MemoEntity의 mno 값)을 호출해서 delete 한다.
    @Test
    public void testDelete(){

        Long mno = 100L;
        memoRepository.deleteById(mno);
    }

    
    @Test
    public void testPageDefault(){
        //1페이지당 10개의 Entity
        Pageable pageable = PageRequest.of(0,10); //.of를 통해 반환받음

        Page<Memo> result= memoRepository.findAll(pageable);

        System.out.println(result);

        for(Memo memo : result.getContent()){
            System.out.println(memo);
        }
        System.out.println("============================");

        System.out.println("Total Pages: " + result.getTotalPages());
        System.out.println("Total Count: " + result.getTotalElements());
        System.out.println("Page Number: " + result.getNumber());
        System.out.println("Page Size: " + result.getSize());
        System.out.println("Has next page?: " + result.hasNext());
        System.out.println("is first page?: " + result.isFirst());
        //sout +엔터 System.out.println


    }
    @Test
    public void testSort(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("mno").descending());
        Page<Memo> result =memoRepository.findAll(pageable);

//        result.get().forEach(memo -> {
//            System.out.println(memo);
//
//        });

        result.get().forEach(memo -> {
            System.out.println("number:" + memo.getMno() + ", content: " +memo.getMemoText());
        });
    }

    @Test
    public void testQueryMethod(){
        List<Memo> result = memoRepository.findByMnoBetweenOrderByMnoDesc(20L, 30L );
        for (Memo memo: result){
            System.out.println(memo.toString());
        }

    }
    @Test
    public void testQueryMethod2(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(20L, 60L,pageable);

        for (Memo memo: result){
            System.out.println(memo.toString());
        }

        System.out.println("================");

        pageable = PageRequest.of(0,10); //0페이지 // 10번까지
        result = memoRepository.findByMnoBetween(20L, 60L,pageable); //20부터~60 사이중에
        result.get().forEach(memo -> {
            System.out.println(memo);
        });

    }

    @Transactional
    @Commit
    @Test
    public void testQueryMethod3(){
        memoRepository.deleteMemoByMnoLessThan(5L);
        testPageDefault();
    }


    @Test
    public void testQueryAnnotationNative(){
        List<Memo> result =memoRepository.getNativeResult();
        for(Memo memo : result){
            System.out.println(memo);
        }
    }

    }

