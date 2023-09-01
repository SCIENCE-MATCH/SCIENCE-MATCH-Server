package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.domain.enumerate.Grade;
import com.sciencematch.sciencematch.domain.enumerate.School;
import com.sciencematch.sciencematch.domain.enumerate.Subject;
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
@SQLDelete(sql = "UPDATE chapter SET deleted = true WHERE chapter_id=?")
@Where(clause = "deleted=false")
public class Chapter {

    @Id
    @GeneratedValue
    @Column(name = "chapter_id")
    private Long id;

    private School school;
    private Grade grade;
    private Subject subject;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id") //join column은 자동으로 pk를 찾아 연결 (name은 말 그대로 내 테이블 내에서 저장할 컬럼의 이름)
    private Chapter parent;

    private Integer listOrder;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<Chapter> children = new ArrayList<>();

    private final Boolean deleted = Boolean.FALSE;

    @Builder
    private Chapter(School school, Grade grade, Subject subject, String description, Chapter parent) {
        this.school = school;
        this.grade = grade;
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

}
