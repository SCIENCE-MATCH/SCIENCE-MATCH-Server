package com.sciencematch.sciencematch;

import com.sciencematch.sciencematch.domain.Admin;
import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.ConnectQuestion;
import com.sciencematch.sciencematch.domain.Question;
import com.sciencematch.sciencematch.domain.QuestionPaper;
import com.sciencematch.sciencematch.domain.Team;
import com.sciencematch.sciencematch.domain.TeamStudent;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.domain.enumerate.Authority;
import com.sciencematch.sciencematch.domain.enumerate.Category;
import com.sciencematch.sciencematch.domain.enumerate.Grade;
import com.sciencematch.sciencematch.domain.enumerate.Level;
import com.sciencematch.sciencematch.domain.enumerate.QuestionTag;
import com.sciencematch.sciencematch.domain.enumerate.School;
import com.sciencematch.sciencematch.domain.enumerate.Subject;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        @Transactional
        public void dbInit() {
            Admin admin = Admin.builder()
                .email("hyh12100863@gmail.com")
                .password(passwordEncoder.encode("hyh12100863@"))
                .authority(Authority.ROLE_ADMIN)
                .build();
            em.persist(admin);

            Teacher teacher = Teacher.builder()
                .email("science@gmail.com")
                .name("김사매")
                .password(passwordEncoder.encode("test1234"))
                .phoneNum("01012345678")
                .authority(Authority.ROLE_TEACHER)
                .build();

            Student student1 = Student.builder()
                .grade("고1")
                .name("김사피")
                .parentNum("01013467946")
                .phoneNum("01087654321")
                .authority(Authority.ROLE_STUDENT)
                .teacher(teacher)
                .build();
            Student student2 = Student.builder()
                .grade("중3")
                .name("김중삼")
                .parentNum("01099482216")
                .phoneNum("01084611579")
                .authority(Authority.ROLE_STUDENT)
                .teacher(teacher)
                .build();

            Team team = Team.builder()
                .name("예시 반")
                .teacher(teacher)
                .build();

            TeamStudent.builder()
                .student(student1)
                .team(team)
                .build();

            TeamStudent.builder()
                .student(student2)
                .team(team)
                .build();

            em.persist(teacher);

            Chapter chapter1 = Chapter.builder()
                .school(School.HIGH)
                .grade(Grade.SECOND)
                .subject(Subject.BIOLOGY)
                .description("1단원")
                .build();

            Chapter chapter2 = Chapter.builder()
                .school(School.HIGH)
                .grade(Grade.SECOND)
                .subject(Subject.BIOLOGY)
                .description("2단원")
                .build();

            Chapter chapter2_1 = Chapter.builder()
                .school(School.HIGH)
                .grade(Grade.SECOND)
                .subject(Subject.BIOLOGY)
                .parent(chapter2)
                .description("소단원")
                .build();

            Chapter chapter2_1_1 = Chapter.builder()
                .school(School.HIGH)
                .grade(Grade.SECOND)
                .subject(Subject.BIOLOGY)
                .parent(chapter2_1)
                .description("개념1")
                .build();

            Chapter chapter2_1_2 = Chapter.builder()
                .school(School.HIGH)
                .grade(Grade.SECOND)
                .subject(Subject.BIOLOGY)
                .parent(chapter2_1)
                .description("개념2")
                .build();

            Chapter chapter3 = Chapter.builder()
                .school(School.HIGH)
                .grade(Grade.SECOND)
                .subject(Subject.BIOLOGY)
                .description("3단원")
                .build();

            Chapter chapter4 = Chapter.builder()
                .school(School.HIGH)
                .grade(Grade.SECOND)
                .subject(Subject.PHYSICS)
                .description("1단원")
                .build();

            em.persist(chapter1);
            em.persist(chapter2);
            em.persist(chapter3);
            em.persist(chapter4);

            Question question = Question.builder()
                .image("https://science-match-bucket.s3.ap-northeast-2.amazonaws.com/question/image/949819a6-3530-4f04-a60f-6e01f516affe.jpg")
                .level(Level.MEDIUM)
                .category(Category.MULTIPLE)
                .answer("1")
                .solution("1이 답")
                .bookName("비상비상")
                .page(10)
                .chapter(chapter2_1_1)
                .build();

            Question question1 = Question.builder()
                .image("https://science-match-bucket.s3.ap-northeast-2.amazonaws.com/question/image/949819a6-3530-4f04-a60f-6e01f516affe.jpg")
                .level(Level.HARD)
                .category(Category.SUBJECTIVE)
                .answer("주관식")
                .solution("이게 답")
                .bookName("비상비상")
                .page(11)
                .chapter(chapter2_1_1)
                .build();

            Question question2 = Question.builder()
                .image("https://science-match-bucket.s3.ap-northeast-2.amazonaws.com/question/image/949819a6-3530-4f04-a60f-6e01f516affe.jpg")
                .level(Level.LOW)
                .category(Category.DESCRIPTIVE)
                .answer("서술형")
                .solution("이게 답??")
                .bookName("비상비상")
                .page(12)
                .chapter(chapter2_1_2)
                .build();

            Question question3 = Question.builder()
                .image("https://science-match-bucket.s3.ap-northeast-2.amazonaws.com/question/image/949819a6-3530-4f04-a60f-6e01f516affe.jpg")
                .level(Level.MEDIUM)
                .category(Category.MULTIPLE)
                .answer("3")
                .solution("3이 답")
                .bookName("비상비상")
                .page(10)
                .chapter(chapter2_1_1)
                .build();

            em.persist(question);
            em.persist(question1);
            em.persist(question2);
            em.persist(question3);

            List<Question> questionList = Arrays.asList(question, question1, question2, question3);

            QuestionPaper questionPaper = QuestionPaper.builder()
                .school(School.HIGH)
                .level(Level.MEDIUM)
                .category(Category.MULTIPLE)
                .questionTag(QuestionTag.NORMAL)
                .title("테스트")
                .makerName("선생님")
                .build();
            em.persist(questionPaper);

            QuestionPaper questionPaper2 = QuestionPaper.builder()
                .school(School.HIGH)
                .level(Level.MEDIUM_HARD)
                .category(Category.SUBJECTIVE)
                .questionTag(QuestionTag.MOCK_EXAM)
                .title("테스트22")
                .makerName("선생님22")
                .build();
            em.persist(questionPaper2);

            for (Question subQuestion : questionList) {
                ConnectQuestion connectQuestion = ConnectQuestion.builder()
                    .question(subQuestion)
                    .questionPaper(questionPaper)
                    .build();
                em.persist(connectQuestion);
            }

            for (Question subQuestion : questionList) {
                ConnectQuestion connectQuestion = ConnectQuestion.builder()
                    .question(subQuestion)
                    .questionPaper(questionPaper2)
                    .build();
                em.persist(connectQuestion);
            }


        }
    }
}
