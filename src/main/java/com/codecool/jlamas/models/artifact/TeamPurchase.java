package com.codecool.jlamas.models.artifact;

import com.codecool.jlamas.models.account.Student;

public class TeamPurchase {

    private Integer id;
    private Artifact artifact;
    private Student student;
    private Integer price;
    private boolean isMarked;

    public TeamPurchase(Integer id, Artifact artifact, Student student, Integer price, boolean isMarked) {
        this.id = id;
        this.artifact = artifact;
        this.student = student;
        this.price = price;
        this.isMarked = isMarked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public Integer isMarkedAsInteger() {
        if (isMarked) {
            return 1;
        } else return 0;
    }
}

//TODO: purchase logic, purchase handler, student handler, student twigs cleanup