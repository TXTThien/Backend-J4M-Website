package org.example.controller.Admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.entity.Category;
import org.example.entity.News;
import org.example.entity.Product;
import org.example.entity.ProductType;
import org.example.repository.AccountRepository;
import org.example.repository.NewsRepository;
import org.example.service.Impl.AccountServiceImpl;
import org.example.service.Impl.NewsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("")
    public ResponseEntity<?> createNews(@RequestBody News news) {
        Integer newsID = news.getNewsID();

        if (newsID != null && newsRepository.findById(newsID).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("News with ID " + newsID + " already exists.");
        }
        try {

            News newNews = newsRepository.save(news);
            return ResponseEntity.status(HttpStatus.CREATED).body(newNews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating product.");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<News> updateNews(@PathVariable("id") int id, @Valid @RequestBody News news) {
        News updateNews = newsService.updateNews(id, news);

        if (updateNews == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity.ok(updateNews);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable("id") int id) {
        News existingNews = newsRepository.findById(id).orElse(null);
        if (existingNews != null) {
            newsService.deleteNews(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

