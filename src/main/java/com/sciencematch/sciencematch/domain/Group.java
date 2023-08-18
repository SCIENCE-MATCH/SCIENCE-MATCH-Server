package com.sciencematch.sciencematch.domain;

import java.util.ArrayList;
import java.util.List;
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
@SQLDelete(sql = "UPDATE group SET deleted = true WHERE group_id=?")
@Where(clause = "deleted=false")
public class Group {

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<GroupStudent> groupStudents = new ArrayList<>();

    private final Boolean deleted = Boolean.FALSE;

    @Builder
    public Group(Teacher teacher) {
        this.teacher = teacher;
    }

}
