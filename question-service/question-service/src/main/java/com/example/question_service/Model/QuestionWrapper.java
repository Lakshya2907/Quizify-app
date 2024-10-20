package com.example.question_service.Model;

import lombok.Data;

@Data
public class QuestionWrapper {
    private int id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public QuestionWrapper(int id, String option4, String option3, String option1,
                           String questionTitle, String option2) {
        this.id = id;
        this.option4 = option4;
        this.option3 = option3;
        this.option1 = option1;
        this.questionTitle = questionTitle;
        this.option2 = option2;
    }
}
