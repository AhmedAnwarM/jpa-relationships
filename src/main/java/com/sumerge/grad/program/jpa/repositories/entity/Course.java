package com.sumerge.grad.program.jpa.repositories.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

import static com.sumerge.grad.program.jpa.constants.Constants.SCHEMA_NAME;
import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "COURSES", schema = SCHEMA_NAME)
public class Course implements Serializable {

    private static final long serialVersionUID = -1069794816450414003L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "HOURS")
    private Integer hours;

    @JoinTable(name = "STUDENT_COURSES", schema = SCHEMA_NAME,
            joinColumns = {@JoinColumn(name = "COURSE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "STUDENT_ID")})
    @ManyToMany(fetch = LAZY, cascade = DETACH)
    private Collection<Student> students;

    @JoinTable(name = "COURSE_INSTRUCTORS", schema = SCHEMA_NAME,
            joinColumns = {@JoinColumn(name = "INSTRUCTOR_ID")},
            inverseJoinColumns = {@JoinColumn(name = "COURSE_ID")})
    @ManyToMany(fetch = LAZY, cascade = DETACH)
    private Collection<Instructor> instructors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

    public Collection<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(Collection<Instructor> instructors) {
        this.instructors = instructors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
