package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.dto.admin.AdminStudentResponseDto;
import com.sciencematch.sciencematch.dto.admin.AdminTeamResponseDto;
import com.sciencematch.sciencematch.dto.admin.WaitingTeacherResponseDto;
import com.sciencematch.sciencematch.dto.chapter.ConceptPostDto;
import com.sciencematch.sciencematch.dto.concept.ConceptResponseDto;
import com.sciencematch.sciencematch.dto.csat.request.CsatIdsRequestDto;
import com.sciencematch.sciencematch.dto.csat.request.CsatRequestDto;
import com.sciencematch.sciencematch.dto.csat.response.CsatAdminResponseDto;
import com.sciencematch.sciencematch.dto.paper_test.PaperTestRequestDto;
import com.sciencematch.sciencematch.dto.paper_test.PaperTestResponseDto;
import com.sciencematch.sciencematch.dto.paper_test.PaperTestSelectDto;
import com.sciencematch.sciencematch.dto.question.AdminQuestionResponseDto;
import com.sciencematch.sciencematch.enums.Authority;
import com.sciencematch.sciencematch.enums.Level;
import com.sciencematch.sciencematch.enums.Subject;
import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.Concept;
import com.sciencematch.sciencematch.domain.Csat;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.domain.TeacherLevel;
import com.sciencematch.sciencematch.domain.Team;
import com.sciencematch.sciencematch.domain.paper_test.PaperTest;
import com.sciencematch.sciencematch.external.client.aws.S3Service;
import com.sciencematch.sciencematch.infrastructure.ChapterRepository;
import com.sciencematch.sciencematch.infrastructure.ConceptRepository;
import com.sciencematch.sciencematch.infrastructure.CsatRepository;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import com.sciencematch.sciencematch.infrastructure.TeacherLevelRepository;
import com.sciencematch.sciencematch.infrastructure.TeacherRepository;
import com.sciencematch.sciencematch.infrastructure.TeamRepository;
import com.sciencematch.sciencematch.infrastructure.paper_test.AssignPaperTestRepository;
import com.sciencematch.sciencematch.infrastructure.paper_test.PaperTestRepository;
import com.sciencematch.sciencematch.infrastructure.question.QuestionRepository;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final TeacherRepository teacherRepository;
    private final TeacherLevelRepository teacherLevelRepository;
    private final StudentRepository studentRepository;
    private final TeamRepository teamRepository;
    private final ConceptRepository conceptRepository;
    private final ChapterRepository chapterRepository;
    private final PaperTestRepository paperTestRepository;
    private final AssignPaperTestRepository assignPaperTestRepository;
    private final CsatRepository csatRepository;
    private final S3Service s3Service;
    private final QuestionRepository questionRepository;

    public List<WaitingTeacherResponseDto> getAllWaitingTeachers() {
        return teacherRepository.findAllByAuthority(Authority.ROLE_GUEST).stream()
            .map(WaitingTeacherResponseDto::of).collect(
                Collectors.toList());
    }

    @Transactional
    public WaitingTeacherResponseDto assignTeacher(Long id) {
        Teacher teacher = teacherRepository.getTeacherById(id);
        teacher.assignTeacher();
        makeTeacherLevel(teacher);
        return WaitingTeacherResponseDto.of(teacher);
    }

    private void makeTeacherLevel(Teacher teacher) {
        teacherLevelRepository.saveAll(Arrays.asList(
            new TeacherLevel(null, Level.HARD, 0.0, 0.0, 0.3, 0.3, 0.4, teacher),
            new TeacherLevel(null, Level.MEDIUM_HARD, 0.0, 0.2, 0.3, 0.3, 0.2, teacher),
            new TeacherLevel(null, Level.MEDIUM, 0.05, 0.3, 0.3, 0.25, 0.1, teacher),
            new TeacherLevel(null, Level.MEDIUM_LOW, 0.2, 0.4, 0.3, 0.1, 0.0, teacher),
            new TeacherLevel(null, Level.LOW, 0.4, 0.4, 0.2, 0.0, 0.0, teacher)
        ));
    }

    public List<WaitingTeacherResponseDto> getAllTeachers() {
        return teacherRepository.findAllByAuthority(Authority.ROLE_TEACHER)
            .stream().map(WaitingTeacherResponseDto::of).collect(Collectors.toList());
    }

    @Transactional
    public WaitingTeacherResponseDto deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.getTeacherById(id);
        teacherRepository.delete(teacher);
        return WaitingTeacherResponseDto.of(teacher);
    }

    //현재는 teacher의 name을 가져오도록 했지만, 나중엔 teacher의 academy를 가져와야 할 수도
    public List<AdminStudentResponseDto> getAllStudents() {
        return studentRepository.findAllByDeleted(false).stream().map(AdminStudentResponseDto::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public AdminStudentResponseDto deleteStudent(Long id) {
        Student student = studentRepository.getStudentById(id);
        studentRepository.delete(student);
        return AdminStudentResponseDto.of(student);
    }

    public List<AdminTeamResponseDto> getAllTeams() {
        return teamRepository.findAll().stream().map(AdminTeamResponseDto::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public AdminTeamResponseDto deleteTeam(Long id) {
        Team team = teamRepository.getTeamById(id);
        teamRepository.delete(team);
        return AdminTeamResponseDto.of(team);
    }

    @Transactional
    public Long postConcept(ConceptPostDto conceptPostDto) throws IOException {
        Chapter chapter = chapterRepository.getChapterById(conceptPostDto.getChapterId());
        Concept postConcept = conceptRepository.getByChapterId(conceptPostDto.getChapterId());
        if (postConcept != null && postConcept.getImage() != null) {
            s3Service.deleteFile(postConcept.getImage());
            conceptRepository.delete(postConcept);
        }

        String uploadImage = s3Service.uploadFile(conceptPostDto.getImage(), "concept");
        String blankImage = s3Service.uploadFile(conceptPostDto.getBlankImage(), "concept/blank");
        Concept concept = Concept.builder()
            .image(uploadImage)
            .blankImage(blankImage)
            .chapter(chapter)
            .build();
        conceptRepository.save(concept);
        return concept.getId();
    }

    public ConceptResponseDto getConcept(Long chapterId) {
        return ConceptResponseDto.of(conceptRepository.getByChapterId(chapterId));
    }

    @Transactional
    public void deleteConcept(Long id) {
        conceptRepository.deleteById(id);
    }

    @Transactional
    public void deletePaperTest(List<Long> paperTestId) {
        for (Long id : paperTestId) {
            assignPaperTestRepository.deleteAllByPaperTestId(id);
        }
        assignPaperTestRepository.flush();
        paperTestRepository.deleteAllByIdInBatch(paperTestId);
    }

    @Transactional
    public void createPaperTest(PaperTestRequestDto paperTestRequestDto) {
        String uploadImage = null;
        if (paperTestRequestDto.getImage() != null) {
            uploadImage = s3Service.uploadFile(paperTestRequestDto.getImage(), "paper-test");
        }
        Chapter chapter = chapterRepository.getChapterById(paperTestRequestDto.getChapterId());
        PaperTest paperTest = PaperTest.builder()
            .school(paperTestRequestDto.getSchool())
            .semester(paperTestRequestDto.getSemester())
            .chapterDescription(chapter.getDescription())
            .image(uploadImage)
            .question(paperTestRequestDto.getQuestion())
            .solution(paperTestRequestDto.getSolution())
            .subject(paperTestRequestDto.getSubject())
            .makerName(paperTestRequestDto.getMakerName())
            .build();
        paperTestRepository.save(paperTest);
    }

    public List<PaperTestResponseDto> getAllPaperTest(
        PaperTestSelectDto preLessonSelectDto) {
        return paperTestRepository.search(preLessonSelectDto);
    }

    public List<CsatAdminResponseDto> getCsat(Subject subject, Integer year, Integer month) {
        return csatRepository.findAllBySubjectAndYearAndMonth(
                subject, year, month).stream().map(CsatAdminResponseDto::of).collect(Collectors.toList());
    }

    public List<AdminQuestionResponseDto> getCsatQuestions(CsatIdsRequestDto csatIdsRequestDto) {
        List<Long> csatIds = csatRepository.findAllBySubjectAndYearInAndMonthInAndPublisherIn(
                csatIdsRequestDto.getSubject(), csatIdsRequestDto.getYear(),
                csatIdsRequestDto.getMonth(), csatIdsRequestDto.getPublisher())
            .stream().map(Csat::getId).collect(Collectors.toList());
        return questionRepository.findAllByCsatIdIn(csatIds).stream().map(AdminQuestionResponseDto::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public void postCsat(CsatRequestDto csatRequestDto) {
        csatRepository.save(Csat.of(csatRequestDto));
    }
}
