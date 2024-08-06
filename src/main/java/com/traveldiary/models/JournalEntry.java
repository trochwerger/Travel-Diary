package com.traveldiary.models;

public class JournalEntry {
    private String title;
    private String date;
    private String content;
    private String imagePath;

    public JournalEntry(String title, String date, String content, String imagePath) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.imagePath = imagePath;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    @Override
    public String toString() {
        return title + " (" + date + ")";
    }
}
