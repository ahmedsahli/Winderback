package com.example.pidev.Service;

import com.example.pidev.Repository.QuestionRepository;
import com.example.pidev.Repository.TestRepository;
import com.example.pidev.entity.Question;
import com.example.pidev.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
public class TestServiceC implements  TestService {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public TestServiceC(TestRepository testRepository, QuestionRepository questionRepository) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
    }

    public Test createTest(Test test){
        return testRepository.save(test);
    }

    public Test addQuestionToTest(Long testId, Long question_id) {
        Test test = testRepository.findById(testId).orElseThrow(EntityNotFoundException::new);;
        Question question = questionRepository.findById(question_id).orElseThrow(EntityNotFoundException::new);
        test.getQuestions().add(question);
        return testRepository.save(test);
    }

    public Test updateTest(Test test) {
        return testRepository.save(test);
    }

    public void deleteTest(Long test_id) {

        testRepository.deleteById(test_id);
    }

    public List<Test> getAllTests() {

        return (List<Test>) testRepository.findAll();
    }

    public Test getTestById(Long test_id) {
        return testRepository.findById(test_id).get();

    }
}
