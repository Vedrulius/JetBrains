package com.mihey;

class Publication {

    private String title;

    public Publication(String title) {
        this.title = title;
    }

    public final String getInfo() {
        return getType() + getDetails();
    }

    public String getType() {
        return "Publication: ";
    }

    public String getDetails() {
        return title;
    }

    public static void main(String[] args) {
        Newspaper np = new Newspaper("Football results", "Sport news");
        Article art = new Article("Why the Sun is hot", "Dr James Smith");
        Announcement ann = new Announcement("Will sell a house", 30);
        Publication p = new Publication("The new era ");

    }
}

class Newspaper extends Publication {

    private String source;

    public Newspaper(String title, String source) {
        super(title);
        this.source = source;
    }

    @Override
    public String getType() {
        return "Newspaper (source - " + source + "): ";
    }

    @Override
    public String getDetails() {
        return super.getDetails();
    }
// write your code here

}

class Article extends Publication {

    private String author;

    public Article(String title, String author) {
        super(title);
        this.author = author;
    }

    @Override
    public String getType() {
        return "Article (author - " + author + "): ";
    }

    @Override
    public String getDetails() {
        return super.getDetails();
    }
// write your code here

}

class Announcement extends Publication {

    private int daysToExpire;

    public Announcement(String title, int daysToExpire) {
        super(title);
        this.daysToExpire = daysToExpire;
    }

    @Override
    public String getType() {
        return "Announcement (days to expire - " + daysToExpire + "): ";
    }

    @Override
    public String getDetails() {
        return super.getDetails();
    }
// write your code here

}
