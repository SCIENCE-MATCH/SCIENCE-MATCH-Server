package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Semester;
import com.sciencematch.sciencematch.Enums.Subject;
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
import javax.persistence.OrderBy;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chapter extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "chapter_id")
    private Long id;

    private School school;
    private Semester semester;
    private Subject subject;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id") //join column은 자동으로 pk를 찾아 연결 (name은 말 그대로 내 테이블 내에서 저장할 컬럼의 이름)
    private Chapter parent;

    private Integer listOrder;

    @OrderBy("listOrder asc")
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private final List<Chapter> children = new ArrayList<>();

    @Builder
    private Chapter(School school, Semester semester, Subject subject, String description, Chapter parent) {
        this.school = school;
        this.semester = semester;
        this.subject = subject;
        this.description = description;
        setParent(parent);
    }

    private void setParent(Chapter parent) {
        if (parent != null) {
            this.parent = parent;
            this.listOrder = parent.children.size() + 1;
            parent.children.add(this);
        }
    }

    public void changeDescription(String description) {
        this.description = description;
    }

}
