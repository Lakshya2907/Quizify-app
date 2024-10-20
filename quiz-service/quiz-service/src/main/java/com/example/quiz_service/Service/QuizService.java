package com.example.quiz_service.Service;

import com.example.quiz_service.Dao.QuizDao;
import com.example.quiz_service.Feign.QuizInterface;
import com.example.quiz_service.Model.QuestionWrapper;
import com.example.quiz_service.Model.Quiz;
import com.example.quiz_service.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category,numQ).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer id) {
        Quiz quiz =  quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> question_wr = quizInterface.getQuestionsFromId(questionIds);

        return question_wr;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
//        System.out.println(responses);
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
