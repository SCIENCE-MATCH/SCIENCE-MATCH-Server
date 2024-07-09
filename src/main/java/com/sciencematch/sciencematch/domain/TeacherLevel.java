package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.DTO.question_paper.TeacherLevelUpdateDto;
import com.sciencematch.sciencematch.Enums.Level;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TeacherLevel {

    @Id
    @GeneratedValue
    @Column(name = "teacher_level_id")
    private Long id;

    private Level level;
    private Double low;
    private Double mediumLow;
    private Double medium;
    private Double mediumHard;
    private Double hard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public List<Integer> getSelectCount(Integer questionNum) {
        return Arrays.asList(
            (int) (questionNum * low),
            (int) (questionNum * mediumLow),
            (int) (questionNum * medium),
            (int) (questionNum * mediumHard),
            (int) (questionNum * hard)
        );
    }

    public void updateTeacherLevel(TeacherLevelUpdateDto teacherLevelUpdateDto) {
        this.low = teacherLevelUpdateDto.getLow().doubleValue() / 100;
        this.mediumLow = teacherLevelUpdateDto.getMediumLow().doubleValue() / 100;
        this.medium = teacherLevelUpdateDto.getMedium().doubleValue() / 100;
        this.mediumHard = teacherLevelUpdateDto.getMediumHard().doubleValue() / 100;
        this.hard = teacherLevelUpdateDto.getHard().doubleValue() / 100;
    }
}
