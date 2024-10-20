package org.example.controller.Admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.entity.News;
import org.example.entity.Origin;
import org.example.repository.AccountRepository;
import org.example.repository.OriginRepository;
import org.example.service.Impl.AccountServiceImpl;
import org.example.service.Impl.OriginServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/origin")
@RequiredArgsConstructor
public class AdminOriginController {
    private final OriginRepository originRepository;
    private final OriginServiceImpl originService;

    @GetMapping("")
    public ResponseEntity<?> getListOrigin(){
        List<Origin> origins = originRepository.findAll();
        if (!origins.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("Origin", origins);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Origin found");
        }
    }
    @PostMapping("")
    public ResponseEntity<?> createOrigin(@RequestBody Origin origin) {
        Integer originID = origin.getOriginID();

        if (originID != null && originRepository.findById(originID).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Origin with ID " + originID + " already exists.");
        }
        try {

            Origin newOrigin = originRepository.save(origin);
            return ResponseEntity.status(HttpStatus.CREATED).body(newOrigin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating product.");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Origin> updateOrigin(@PathVariable("id") int id, @Valid @RequestBody Origin origin) {
        Origin updateOrigin = originService.updateOrigin(id, origin);

        if (updateOrigin == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity.ok(updateOrigin);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrigin(@PathVariable("id") int id) {
        Origin existingNews = originRepository.findById(id).orElse(null);
        if (existingNews != null) {
            originService.deleteOrigin(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
