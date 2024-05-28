package com.example.nationalgallery;

public class listitem {
    private String title;
    private String description;
    private String imageUrl;

    public listitem() {
        // Порожній конструктор, необхідний для Firebase
    }

    public listitem(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description; // Додайте поле для опису
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
