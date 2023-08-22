package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE team SET deleted = true WHERE team_id=?")
@Where(clause = "deleted=false")
public class Team extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<TeamStudent> teamStudents;

    private final Boolean deleted = Boolean.FALSE;

    //반 생성시 선택한 학생들의 id로 조회해 리스트로 넘겨서 그룹 생성
    @Builder
    public Team(String name, Teacher teacher) {
        this.name = name;
        setTeacher(teacher);
        this.teamStudents = new ArrayList<>();
    }

    private void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        teacher.getTeam().add(this);
    }

    public void updateGroupDetail(String name) {
        this.name = name;
        this.teamStudents = new ArrayList<>();
    }

}
