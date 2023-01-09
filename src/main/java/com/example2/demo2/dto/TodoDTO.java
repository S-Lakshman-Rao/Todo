package com.example2.demo2.dto;

import com.example2.demo2.model.TodoItem;

public class TodoDTO {

    private long id;

    private String title;

    private boolean done;

    private Integer userid;

    public TodoDTO() {
    }

    public TodoDTO(TodoItem item) {
        this.id = item.getId();
        this.title = item.getTitle();
        this.done = item.isDone();
        this.userid = item.getUserid();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
