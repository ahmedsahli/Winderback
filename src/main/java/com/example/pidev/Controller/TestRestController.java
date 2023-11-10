package com.example.pidev.Controller;

import com.example.pidev.Repository.OptionRepository;
import com.example.pidev.Repository.QuestionRepository;
import com.example.pidev.Repository.TestRepository;
import com.example.pidev.Service.QuestionServiceC;
import com.example.pidev.Service.TestServiceC;
import com.example.pidev.entity.Option;
import com.example.pidev.entity.Question;
import com.example.pidev.entity.Test;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/tests")
public class TestRestController {
 
    TestServiceC testService;
    QuestionRepository questionRepository ;
    TestRepository testRepository ;
    QuestionServiceC questionService;
    OptionRepository optionRepository;

    //Permits to get all the tests in the database
    // http://localhost:8089/pidev/tests/retrieve-all-tests
    @GetMapping("/retrieve-all-tests")
    public List<Test> getTests() {
        List<Test> listTests = testService.getAllTests();
        return listTests;
    }

    //Permits to get all the questions related to a specific test
    // http://localhost:8089/pidev/tests/{test_id}/questions
    @GetMapping("/{test_id}/questions")
    public List<Question> getQuestionsForTest(@PathVariable Long test_id) {
        Test test = testService.getTestById(test_id);
        return test.getQuestions();
    }

    //Permits to get a test by id
    // http://localhost:8089/pidev/tests/retrieve-test/8
    @GetMapping("/retrieve-test/{test-id}")
    public Test retrieveTest(@PathVariable("test-id") Long TestId) {
        return testService.getTestById(TestId);
    }

    //Permits to add a test
    // http://localhost:8089/pidev/tests/add-test
    @PostMapping("/add-test")
    public Test addTest(@RequestBody Test test) {
        Test test1 = testService.createTest(test);
        return test1;
    }

    //Permits to add a question to a specific test
    // http://localhost:8089/pidev/tests/{test_id}/questions/{question_id}
    @PostMapping("/{test_id}/questions/{question_id}")
    public Test addQuestionToTest(@PathVariable("test_id") Long test_id, @PathVariable("question_id") Long question_id) {
        Test test = testRepository.findById(test_id).orElse(null);
        Question question = questionRepository.findById(question_id).orElseThrow(() -> new EntityNotFoundException("Entity with ID " + question_id + " not found"));
        test.getQuestions().add(question);
        return testRepository.save(test);
    }

    //Permits to delete a test
    // http://localhost:8089/pidev/tests/remove-test/1
    @DeleteMapping("/remove-test/{test_id}")
    public void removeTest(@PathVariable("test_id") Long test_id) {
        testService.deleteTest(test_id);
    }

 /*   // http://localhost:8089/pidev/tests/update-test
    @PutMapping("/update-test")
    public Test updateTest(@RequestBody Test test) {
        Test test1 = testService.updateTest(test);
        return test1;
    } */

    //Permits test taker to change a chosen question of a specific test
    // http://localhost:8089/pidev/tests/update-test/{test_id}/questions/{question_id}
    @PutMapping("/update-test/{test_id}/questions/{question_id}")
    public List<Question> updateQuestionInTest(@PathVariable("test_id") Long test_id, @PathVariable("question_id") Long question_id) {
        Test test = testRepository.findById(test_id).orElse(null);
        Question question = questionRepository.findById(question_id).orElse(null);
        if (test.getQuestions().contains(question)) {
            List<Question> questions = test.getQuestions();
            int occurrences = Collections.frequency(questions, question);
            Question rand = null;
            do {
                rand = changeQuestion();
            } while (questions.contains(rand));
            test.getQuestions().remove(question);
            test.getQuestions().add(rand);
            testService.updateTest(test);
        }
        return getQuestionsForTest(test_id);
    }

    public Question changeQuestion() {
        List<Question> listQuestions = questionService.getAllQuestions();
        Collections.shuffle(listQuestions);
        Question randQuestion = listQuestions.get(0);
        return randQuestion;
    }

    //Permits to create a test as long as its questions and options
    // http://localhost:8089/pidev/tests/create-test
    @PostMapping("/create-test")
    public Test createTestWithQuestionsAndOptions(@RequestBody Test test) {
        Test test1 = new Test();
        test1.setName(test.getName());
        test1.setDescription(test.getDescription());
        test1.setStack(test.getStack());
        test1.setLevel(test.getLevel());
        List<Question> questions = new ArrayList<>();
        for (Question question : test.getQuestions()) {
            Question question1 = new Question();
            question1.setText(question.getText());
            question1.setCorrect_option(question.getCorrect_option());

            List<Option> options = new ArrayList<>();
            for (Option option : question.getOptions()) {
                Option option1 = new Option();
                option1.setText(option.getText());
                option.setQuestion(question);
                options.add(option);
            }

            question.setOptions(options);
            questions.add(question);
        }

        test.setQuestions(questions);

        Test savedTest = testService.createTest(test);

        return savedTest;
    }

    //Permits to modify a specific test
    @PutMapping("/update-test/{test_id}")
    public Test updateTest(@PathVariable("test_id") long test_id, @RequestBody Test test) {
        Optional<Test> testData = testRepository.findById(test_id);
        Test test1 = testData.get();

        test1.setName(test.getName());
        test1.setDescription(test.getDescription());
        test1.setStack(test.getStack());
        test1.setLevel(test.getLevel());
        List<Question> questions = new ArrayList<>();
        for (Question question : test.getQuestions()) {
            Question question1 = new Question();
            question1.setText(question.getText());
            question1.setCorrect_option(question.getCorrect_option());

            List<Option> options = new ArrayList<>();
            for (Option option : question.getOptions()) {
                Option option1 = new Option();
                option1.setText(option.getText());
                option1.setQuestion(question1);
                options.add(option1);
            }

            question1.setOptions(options);
            questions.add(question1);
        }

        test1.setQuestions(questions);

        Test savedTest = testService.createTest(test1);

        return savedTest;
    }
}
