package com.example.todo_app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import kotlin.experimental.ExperimentalTypeInference;
public class Item {

    private String title;
    private int priority;
    private String description;

    private String responsiblePerson;
    private Long dueDate;
    private Long createDate;

    public Item(String title, int priority, String description,
                String responsiblePerson, Long dueDate, Long createDate) {

        this.title = title;
        this.priority = priority;
        this.description = description;
        this.responsiblePerson = responsiblePerson;
        this.dueDate = dueDate;
        this.createDate = createDate;
    }
    public Item() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public Long getDueDate() {
        return dueDate;
    }

    public void setDueDate(Long dueDate) {
        this.dueDate = dueDate;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }
}
