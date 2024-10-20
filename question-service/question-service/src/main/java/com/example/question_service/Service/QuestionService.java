package com.example.question_service.Service;

import com.example.question_service.Dao.QuestionDao;
import com.example.question_service.Model.QuestionWrapper;
import com.example.question_service.Model.Questions;
import com.example.question_service.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService
{
    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Questions>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAll(),HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<List<Questions>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Questions question) {
        questionDao.save(question);
        try{
            return new ResponseEntity<>("Success",HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>("Success",HttpStatus.BAD_REQUEST);

    }

    public String deleteQuestion(int id) {
        questionDao.deleteById(id);
        return "Deleted";
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName,numQuestions);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Questions> question_list = new ArrayList<>();
        for(Integer id : questionIds){
            question_list.add(questionDao.findById(id).get());
        }

        for(Questions q : question_list){
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),
                    q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());

            wrappers.add(qw);
        }
        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int ans = 0;
        for(Response response:responses){
            Questions question = questionDao.findById(response.getId()).get();
            if(question.getRightAnswer().equals(response.getResponse())){
                ans+=4;
            }
            else{
                ans--;
            }
        }

        return new ResponseEntity<>(ans,HttpStatus.OK);
    }
}
