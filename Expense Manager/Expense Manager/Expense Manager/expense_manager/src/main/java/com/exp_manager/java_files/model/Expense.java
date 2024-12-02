package com.exp_manager.java_files.model;

public class Expense {
    private Object id;
    private Object amount;
    private Object date;
    private Object time;
    private Object description;
    private Object category;
    private String documentId;

    public Expense(String documentId, Object amount, Object date, Object time, Object description, Object category) {
        this.documentId = documentId;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.description = description;
        this.category = category;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount.toString();
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date.toString();
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time.toString();
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description.toString();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category.toString();
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDocumentId() {
        return this.documentId;
    }
}
