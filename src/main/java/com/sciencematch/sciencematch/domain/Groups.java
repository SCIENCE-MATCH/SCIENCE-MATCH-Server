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
@SQLDelete(sql = "UPDATE groups SET deleted = true WHERE groups_id=?")
@Where(clause = "deleted=false")
public class Groups extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "groups_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "groups", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<GroupStudent> groupStudents = new ArrayList<>();

    private final Boolean deleted = Boolean.FALSE;

    //반 생성시 선택한 학생들의 id로 조회해 리스트로 넘겨서 그룹 생성
    @Builder
    public Groups(String name, Teacher teacher) {
        this.name = name;
        setTeacher(teacher);
    }

    private void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        teacher.getGroups().add(this);
    }

    public void updateGroupDetail(String name) {
        this.name = name;
        this.groupStudents = new ArrayList<>();
    }

}
