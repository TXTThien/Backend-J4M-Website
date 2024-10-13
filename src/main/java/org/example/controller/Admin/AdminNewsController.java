package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.News;
import org.example.repository.AccountRepository;
import org.example.repository.NewsRepository;
import org.example.service.Impl.AccountServiceImpl;
import org.example.service.Impl.NewsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/news")
@RequiredArgsConstructor
public class AdminNewsController {
    private final NewsRepository newsRepository;
    private final NewsServiceImpl newsService;

    @GetMapping("")
    public ResponseEntity<?> getListNews(){
        List<News> news = newsRepository.findAll();
        if (!news.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("News", news);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No News found");
        }
    }
}
