package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.DTO.admin.AdminStudentResponseDto;
import com.sciencematch.sciencematch.DTO.admin.AdminTeamResponseDto;
import com.sciencematch.sciencematch.DTO.admin.WaitingTeacherResponseDto;
import com.sciencematch.sciencematch.DTO.chapter.ConceptPostDto;
import com.sciencematch.sciencematch.DTO.concept.ConceptResponseDto;
import com.sciencematch.sciencematch.Enums.Authority;
import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.Concept;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.domain.Team;
import com.sciencematch.sciencematch.external.client.aws.S3Service;
import com.sciencematch.sciencematch.infrastructure.ChapterRepository;
import com.sciencematch.sciencematch.infrastructure.ConceptRepository;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import com.sciencematch.sciencematch.infrastructure.TeacherRepository;
import com.sciencematch.sciencematch.infrastructure.TeamRepository;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final TeamRepository teamRepository;
    private final ConceptRepository conceptRepository;
    private final ChapterRepository chapterRepository;
    private final S3Service s3Service;

    public List<WaitingTeacherResponseDto> getAllWaitingTeachers() {
        return teacherRepository.findAllByAuthority(Authority.ROLE_GUEST).stream()
            .map(WaitingTeacherResponseDto::of).collect(
                Collectors.toList());
    }

    @Transactional
    public WaitingTeacherResponseDto assignTeacher(Long id) {
        Teacher teacher = teacherRepository.getTeacherById(id);
        teacher.assignTeacher();
        return WaitingTeacherResponseDto.of(teacher);
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
        if (postConcept.getImage() != null) {
            s3Service.deleteFile(postConcept.getImage());
            conceptRepository.delete(postConcept);
        }

        String uploadImage = s3Service.uploadImage(conceptPostDto.getImage(), "concept");
        Concept concept = Concept.builder()
            .image(uploadImage)
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
}
