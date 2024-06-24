package com.sciencematch.sciencematch;

import com.sciencematch.sciencematch.Enums.AssignStatus;
import com.sciencematch.sciencematch.Enums.Authority;
import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.Enums.Level;
import com.sciencematch.sciencematch.Enums.QuestionTag;
import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Semester;
import com.sciencematch.sciencematch.Enums.Subject;
import com.sciencematch.sciencematch.domain.Admin;
import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.domain.Team;
import com.sciencematch.sciencematch.domain.TeamStudent;
import com.sciencematch.sciencematch.domain.paper_test.AssignPaperTest;
import com.sciencematch.sciencematch.domain.paper_test.PaperTest;
import com.sciencematch.sciencematch.domain.question.AssignQuestions;
import com.sciencematch.sciencematch.domain.question.ConnectQuestion;
import com.sciencematch.sciencematch.domain.question.Question;
import com.sciencematch.sciencematch.domain.question.QuestionPaper;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

//    @PostConstruct
//    public void init() {
//        initService.dbInit();
//    }

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
                .password("01087654321")
                .authority(Authority.ROLE_STUDENT)
                .teacher(teacher)
                .build();
            Student student2 = Student.builder()
                .grade("중3")
                .name("김중삼")
                .parentNum("01099482216")
                .phoneNum("01084611579")
                .password("01084611579")
                .authority(Authority.ROLE_STUDENT)
                .teacher(teacher)
                .build();

            Team team = Team.builder()
                .name("예시 반")
                .teacherName("예시 선생 이름")
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
                .semester(Semester.SECOND1)
                .subject(Subject.BIOLOGY)
                .description("1단원")
                .build();

            Chapter chapter2 = Chapter.builder()
                .school(School.HIGH)
                .semester(Semester.SECOND1)
                .subject(Subject.BIOLOGY)
                .description("2단원")
                .build();

            Chapter chapter2_1 = Chapter.builder()
                .school(School.HIGH)
                .semester(Semester.SECOND1)
                .subject(Subject.BIOLOGY)
                .parent(chapter2)
                .description("소단원")
                .build();

            Chapter chapter2_1_1 = Chapter.builder()
                .school(School.HIGH)
                .semester(Semester.SECOND1)
                .subject(Subject.BIOLOGY)
                .parent(chapter2_1)
                .description("개념1")
                .build();

            Chapter chapter2_1_2 = Chapter.builder()
                .school(School.HIGH)
                .semester(Semester.SECOND1)
                .subject(Subject.BIOLOGY)
                .parent(chapter2_1)
                .description("개념2")
                .build();

            Chapter chapter3 = Chapter.builder()
                .school(School.HIGH)
                .semester(Semester.SECOND1)
                .subject(Subject.BIOLOGY)
                .description("3단원")
                .build();

            Chapter chapter4 = Chapter.builder()
                .school(School.HIGH)
                .semester(Semester.SECOND1)
                .subject(Subject.PHYSICS)
                .description("1단원")
                .build();

            em.persist(chapter1);
            em.persist(chapter2);
            em.persist(chapter3);
            em.persist(chapter4);

            Question question = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.MEDIUM)
                .category(Category.MULTIPLE)
                .solution("1")
                .solutionImg("https://i.pinimg.com/736x/57/07/21/5707216fdf56be1997e8e6309b06c85e.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(3)
                .build();

            Question question1 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.HARD)
                .category(Category.SUBJECTIVE)
                .solution("주관식")
                .solutionImg("https://i.pinimg.com/236x/5f/a3/b6/5fa3b60a29aa9c9c50cfc3b00c76fa41.jpg")
                .bookName("비상비상")
                .page(11)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(2)
                .build();

            Question question2 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.LOW)
                .category(Category.DESCRIPTIVE)
                .solution("서술형")
                .solutionImg("https://i.pinimg.com/236x/5f/a3/b6/5fa3b60a29aa9c9c50cfc3b00c76fa41.jpg")
                .bookName("비상비상")
                .page(12)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(2)
                .build();

            Question question3 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.MEDIUM)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(2)
                .build();

            Question question4 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.LOW)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(2)
                .build();

            Question question5 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.LOW)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_2.getId())
                .score(2)
                .build();

            Question question6 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.LOW)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(2)
                .build();

            Question question7 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.LOW)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(2)
                .build();

            Question question8 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.LOW)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_2.getId())
                .score(2)
                .build();

            Question question9 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.LOW)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(2)
                .build();

            Question question10 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.LOW)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(2)
                .build();

            Question question11 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.HARD)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(3)
                .build();

            Question question12 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.HARD)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(3)
                .build();

            Question question13 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.HARD)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(3)
                .build();

            Question question14 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.HARD)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(3)
                .build();

            Question question15 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.HARD)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(3)
                .build();

            Question question16 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.MEDIUM_HARD)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(3)
                .build();

            Question question17 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.MEDIUM_HARD)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(3)
                .build();

            Question question18 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.MEDIUM_HARD)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(3)
                .build();

            Question question19 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.MEDIUM_HARD)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(3)
                .build();

            Question question20 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.MEDIUM_HARD)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(3)
                .build();

            Question question21 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.MEDIUM_HARD)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(4)
                .build();

            Question question22 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.MEDIUM_HARD)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(4)
                .build();

            Question question23 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.MEDIUM_HARD)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(4)
                .build();

            Question question24 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.MEDIUM_LOW)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(4)
                .build();
            Question question25 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.MEDIUM_LOW)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(4)
                .build();
            Question question26 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.MEDIUM_LOW)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(4)
                .build();
            Question question27 = Question.builder()
                .school(School.HIGH)
                .semester(Semester.FIRST1)
                .subject(Subject.BIOLOGY)
                .image(
                    "https://i.pinimg.com/736x/89/78/d5/8978d55ffbfc0133d0510ea3bbf773e3.jpg")
                .level(Level.MEDIUM)
                .category(Category.MULTIPLE)
                .solution("3")
                .solutionImg("https://i.pinimg.com/736x/e2/9f/f5/e29ff57a77b5c00fbd5dd7e517c682dc.jpg")
                .bookName("비상비상")
                .page(10)
                .questionTag(QuestionTag.NORMAL)
                .chapterId(chapter2_1_1.getId())
                .score(4)
                .build();

            em.persist(question);
            em.persist(question1);
            em.persist(question2);
            em.persist(question3);
            em.persist(question4);
            em.persist(question5);
            em.persist(question6);
            em.persist(question7);
            em.persist(question8);
            em.persist(question9);
            em.persist(question10);
            em.persist(question11);
            em.persist(question12);
            em.persist(question13);
            em.persist(question14);
            em.persist(question15);
            em.persist(question16);
            em.persist(question17);
            em.persist(question18);
            em.persist(question19);
            em.persist(question20);
            em.persist(question21);
            em.persist(question22);
            em.persist(question23);
            em.persist(question24);
            em.persist(question25);
            em.persist(question26);
            em.persist(question27);

            List<Question> questionList = Arrays.asList(question, question1, question2, question3);
            List<Question> questionList2 = Arrays.asList(question4, question5, question6, question7);

            QuestionPaper questionPaper = QuestionPaper.builder()
                .questionNum(4)
                .school(School.HIGH)
                .category(Category.MULTIPLE)
                .questionTag(QuestionTag.NORMAL)
                .title("테스트")
                .makerName("선생님")
                .subject(Subject.PHYSICS)
                .build();
            em.persist(questionPaper);

            QuestionPaper questionPaper2 = QuestionPaper.builder()
                .questionNum(4)
                .school(School.HIGH)
                .category(Category.SUBJECTIVE)
                .questionTag(QuestionTag.MOCK_EXAM)
                .title("테스트22")
                .makerName("선생님22")
                .subject(Subject.BIOLOGY)
                .build();
            em.persist(questionPaper2);

            QuestionPaper questionPaper3 = QuestionPaper.builder()
                .questionNum(4)
                .school(School.HIGH)
                .category(Category.SUBJECTIVE)
                .questionTag(QuestionTag.MOCK_EXAM)
                .title("테스트33")
                .makerName("선생님22")
                .subject(Subject.BIOLOGY)
                .build();
            em.persist(questionPaper3);

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

            for (Question subQuestion : questionList2) {
                ConnectQuestion connectQuestion = ConnectQuestion.builder()
                    .question(subQuestion)
                    .questionPaper(questionPaper3)
                    .build();
                em.persist(connectQuestion);
            }

            AssignQuestions assignQuestions1 = AssignQuestions.builder()
                .questionPaper(questionPaper)
                .student(student1)
                .subject(questionPaper.getSubject())
                .assignStatus(AssignStatus.WAITING)
                .build();

            AssignQuestions assignQuestions2 = AssignQuestions.builder()
                .questionPaper(questionPaper2)
                .student(student1)
                .subject(questionPaper2.getSubject())
                .assignStatus(AssignStatus.COMPLETE)
                .build();

            AssignQuestions assignQuestions3 = AssignQuestions.builder()
                .questionPaper(questionPaper2)
                .student(student1)
                .subject(questionPaper2.getSubject())
                .assignStatus(AssignStatus.GRADED)
                .build();

            AssignQuestions assignQuestions4 = AssignQuestions.builder()
                .questionPaper(questionPaper3)
                .student(student1)
                .subject(questionPaper3.getSubject())
                .assignStatus(AssignStatus.WAITING)
                .build();

            AssignQuestions assignQuestions5 = AssignQuestions.builder()
                .questionPaper(questionPaper2)
                .student(student1)
                .subject(questionPaper2.getSubject())
                .assignStatus(AssignStatus.WAITING)
                .build();

            em.persist(assignQuestions1);
            em.persist(assignQuestions2);
            em.persist(assignQuestions3);
            em.persist(assignQuestions4);
            em.persist(assignQuestions5);

            PaperTest paperTest = PaperTest.builder()
                .school(School.HIGH)
                .semester(Semester.SECOND1)
                .subject(Subject.SCIENCE)
                .chapterDescription("단원")
                .question("문제1")
                .solution("해결")
                .build();

            em.persist(paperTest);

            AssignPaperTest paperTest1 = AssignPaperTest.builder()
                .paperTest(paperTest)
                .student(student1)
                .subject(paperTest.getSubject())
                .build();

            em.persist(paperTest1);

        }
    }
}
