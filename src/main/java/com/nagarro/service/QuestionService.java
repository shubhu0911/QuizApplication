package com.nagarro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nagarro.dao.QuestionDao;
import com.nagarro.exception.QuestionNotFoundException;
import com.nagarro.model.Question;

@Service
public class QuestionService {

	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			return new ResponseEntity(questionDao.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(new ArrayList<>(), HttpStatus.BAD_REQUEST);

	}

	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		try{
			return new ResponseEntity(questionDao.findByCategory(category),HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(questionDao.findByCategory(category),HttpStatus.BAD_REQUEST);
 
	}

	public ResponseEntity<String> addQuestion(Question question) {
		questionDao.save(question);
		return new ResponseEntity<>("Success",HttpStatus.CREATED);
	}

	public Question updateQuestion(Question updatedQuestion) {
		// Assuming that the question exists in the database
		// You may want to add validation to check if the question exists
		Optional<Question> existingQuestion = questionDao.findById(updatedQuestion.getId());

		if (existingQuestion.isPresent()) {
			// Update the existing question
			return questionDao.save(updatedQuestion);
		} else {
			// Throw an exception or handle the case where the question does not exist
			throw new QuestionNotFoundException("Question with ID " + updatedQuestion.getId() + " not found");
		}
	}

	public void deleteQuestion(Integer id) {
		// Check if the question exists
		if (questionDao.existsById(id)) {
			questionDao.deleteById(id);
		} else {
			throw new QuestionNotFoundException("Question with ID " + id + " not found");
		}
	}

}
