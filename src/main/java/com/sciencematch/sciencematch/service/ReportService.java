package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.domain.Report;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.dto.report.ReportCreateDto;
import com.sciencematch.sciencematch.dto.report.ReportResponseDto;
import com.sciencematch.sciencematch.exception.model.NotFoundException;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.external.client.aws.S3Service;
import com.sciencematch.sciencematch.infrastructure.ReportRepository;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import com.sciencematch.sciencematch.infrastructure.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ReportService {

    private final ReportRepository reportRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final S3Service s3Service;

    public List<ReportResponseDto> getReports(String email){
        return reportRepository.findAllByTeacherEmail(email);
    }
    public List<ReportResponseDto> getAllReports(){
        return reportRepository.findAllReports();
    }

    @Transactional
    public void createReport(ReportCreateDto reportCreateDto, String email) {
        Student student = studentRepository.findById(reportCreateDto.getStudentId())
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, "학생을 찾을 수 없습니다."));

        Teacher teacher = teacherRepository.getTeacherByEmail(email);
        String reportUrl = s3Service.uploadFile(reportCreateDto.getPdf(),"report");

        Report report = Report.builder()
                .title(reportCreateDto.getTitle())
                .period(reportCreateDto.getPeriod())
                .pdf(reportUrl)
                .student(student)
                .teacher(teacher)
                .build();
        reportRepository.save(report);
    }

    @Transactional
    public void deleteReports(List<Long> reportIds, String email) {
        List<Report> reports = reportRepository.findAllById(reportIds);

        for (Report report : reports) {
            if (!report.getTeacher().getName().equals(email)) {
                throw new NotFoundException(ErrorStatus.FORBIDDEN_USER_EXCEPTION, "삭제 권한이 없는 보고서가 포함되어 있습니다.");
            }
        }

        reportRepository.deleteAll(reports);
    }
}
