package org.example.service;

import org.example.entity.News;

import java.util.List;

public interface INewsService {
    News updateNews (int id, News news);
    void deleteNews(int id);

    List<News> find3NewsEnable();
}
