package com.example.pidev.Controller;

import com.example.pidev.Repository.OptionRepository;
import com.example.pidev.Repository.QuestionRepository;
import com.example.pidev.Service.OptionServiceC;
import com.example.pidev.Service.QuestionServiceC;
import com.example.pidev.entity.Option;
import com.example.pidev.entity.Question;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/questions")
public class QuestionRestController {

    @Autowired


    QuestionServiceC questionService;
    OptionServiceC optionService;
    QuestionRepository questionRepository ;
    OptionRepository optionRepository;



    // http://localhost:8089/pidev/questions/retrieve-all-questions
    @GetMapping("/retrieve-all-questions")
    public List<Question> getQuestions() {
        List<Question> listQuestions = questionService.getAllQuestions();
        return listQuestions;
    }

    // http://localhost:8089/pidev/questions/retrieve-selected-questions
    @GetMapping("/retrieve-selected-questions")
    public List<Question> getRandomQuestions() {
        List<Question> selectedQuestions = questionService.selectRandomQuestions();
        return selectedQuestions ;
    }


    // http://localhost:8089/pidev/questions/retrieve-question/8
    @GetMapping("/retrieve-question/{question-id}")
    public Question retrieveQuestion(@PathVariable("question-id") Long QuestionId) {
        return questionService.getQuestionById(QuestionId);
    }


    // http://localhost:8089/pidev/questions/add-question
    @PostMapping("/add-question")
    public Question addQuestion(@RequestBody Question question) {
        Question question1 = questionService.createQuestion(question);
        return question1;
    }


    // http://localhost:8089/pidev/questions/remove-question/1
    @DeleteMapping("/remove-question/{question-id}")
    public void removeQuestion(@PathVariable("question-id") Long questionId) {
        questionService.deleteQuestion(questionId);
    }

    // http://localhost:8089/pidev/questions/update-question
    @PutMapping("/update-question")
    public Question updateQuestion(@RequestBody Question question) {
        Question question1 = questionService.updateQuestion(question);
        return question1;
    }























}
