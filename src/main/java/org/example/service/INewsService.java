package org.example.service;

import org.example.entity.News;

public interface INewsService {
    News updateNews (int id, News news);
    void deleteNews(int id);
}
