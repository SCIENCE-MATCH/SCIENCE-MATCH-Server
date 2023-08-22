package com.sciencematch.sciencematch.domain;


import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamStudent extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "team_student_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // 연관 관계 편의 메서드
    public void setTeam(Team team) {
        this.team = team;
        if (!team.getTeamStudents().contains(this)) {
            team.getTeamStudents().add(this);
        }
    }

    public void setStudent(Student student) {
        this.student = student;
        if (!student.getTeamStudents().contains(this)) {
            student.getTeamStudents().add(this);
        }
    }

    @Builder
    public TeamStudent(Team team, Student student) {
        setTeam(team);
        setStudent(student);
    }
}
