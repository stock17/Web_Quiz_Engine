package engine;

import entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class QuizController {

    @Autowired
    QuizRepository quizRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CompletionRepository completionRepository;

    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable int id){
        return quizRepository.findById(id).get();
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable int id, Authentication auth){
        Quiz quiz = quizRepository.findById(id).get();
        if (quiz.getAuthor().equals(auth.getName())) {
            quizRepository.delete(quiz);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/api/quizzes/{id}/solve", consumes = "application/json")
    public Result checkAnswer (@PathVariable int id,
                               @RequestBody Map<String, Integer[]> body,
                               Authentication auth) {
        Result result;
        Integer[] answer = body.get("answer");
        System.out.println(answer.getClass().getSimpleName());
        Quiz quiz = quizRepository.findById(id).get();
        if (Arrays.equals(answer, quiz.getAnswer())){
            result = Result.OK;
            long user_id = userRepository.findByEmail(auth.getName()).get().getId();
            completionRepository.save(new Completion(user_id, id, LocalDateTime.now()));
        } else {
            result = Result.ERROR;
        }
        return result;
    }

    @PostMapping(value = "/api/quizzes", consumes = "application/json")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz, Authentication auth) {
        System.out.println(auth.getName());
        quiz.setAuthor(auth.getName());
        quizRepository.save(quiz);
        System.out.println(quiz);
        return quiz;
    }

    @GetMapping(value = "/api/quizzes")
    public Page<Quiz> quizzes (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);
        Page<Quiz> quizPage = quizRepository.findAll(paging);
        return quizPage;
    }

    @GetMapping(value = "/api/quizzes/completed")
    public Page<Completion> completions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            Authentication auth){
        long userId = userRepository.findByEmail(auth.getName()).get().getId();
        Page<Completion> completionPage = completionRepository
                .getAllByUserId(userId, PageRequest.of(page, pageSize,
                        Sort.by(Sort.Direction.DESC, "completedAt")));
        return completionPage;
    }
}
