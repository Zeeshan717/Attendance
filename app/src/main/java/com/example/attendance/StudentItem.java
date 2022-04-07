package com.example.attendance;

public class StudentItem {
    private String id;
    private String name;
    private String status;

    public StudentItem(String id, String name) {
        this.id = id;
        this.name = name;
        status="";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
