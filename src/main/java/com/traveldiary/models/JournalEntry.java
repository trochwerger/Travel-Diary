package com.traveldiary.models;

public class JournalEntry {

    private String title;
    private String date;
    private String content;
    private String imagePath;
    private String user;

    // Constructor
    public JournalEntry(String title, String date, String content, String imagePath, String user) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.imagePath = imagePath;
        this.user = user;
    }

    // Getter and setter methods
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return title + "|" + date + "|" + content + "|" + imagePath + "|" + user;
    }

    public static JournalEntry fromString(String entryString) {
        String[] parts = entryString.split("\\|");
        if (parts.length == 5) {
            return new JournalEntry(parts[0], parts[1], parts[2], parts[3], parts[4]);
        }
        return null;
    }
}
