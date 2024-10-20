package com.example.question_service.Dao;

import com.example.question_service.Model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Questions,Integer> {

    List<Questions> findByCategory(String category);

//    @Query(value = "SELECT * from questions q where q.category =: category order by random() limit:numQ"
//            ,nativeQuery = true)
//    List<Questions> findRandomQuestionsByCategory(String category, int numQ);

    @Query(value = "select q.id from questions q where q.category = :category order by random() limit :numQ", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(@Param("category") String category, @Param("numQ") int numQ);


}
