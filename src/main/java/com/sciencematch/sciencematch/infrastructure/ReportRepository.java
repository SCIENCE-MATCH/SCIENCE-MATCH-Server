package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Report;
import com.sciencematch.sciencematch.dto.report.ReportResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    // 선생 ID로 보고서 전체 조회
    @Query("select rp from Report rp where rp.teacher.name = :teacherName")
    List<ReportResponseDto> findAllByTeacherEmail(@Param("teacherName") String teacherName);

}
