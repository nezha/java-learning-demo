package com.nezha.learn.demo.stream.group;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: Article
 *
 * @author: chentong
 * Date:     2019/1/3 20:22
 */
public class Article {
    String title;
    String author;
    ArticleType type;
    int likes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArticleType getType() {
        return type;
    }

    public void setType(ArticleType type) {
        this.type = type;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
