package org.example.controller.Admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.entity.Review;
import org.example.entity.Size;
import org.example.repository.ProductTypeRepository;
import org.example.repository.SizeRepository;
import org.example.service.Impl.ProductTypeServiceImpl;
import org.example.service.Impl.SizeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/size")
@RequiredArgsConstructor
public class AdminSizeController {
    private final SizeRepository sizeRepository;
    private final SizeServiceImpl sizeService;

    @GetMapping("")
    public ResponseEntity<?> getListSize(){
        List<Size> sizes = sizeRepository.findAll();
        if (!sizes.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("Size", sizes);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Size found");
        }
    }
    @PostMapping("")
    public ResponseEntity<?> createSize(@RequestBody Size size) {
        Integer sizeID = size.getSizeID();

        if (sizeID != null && sizeRepository.findById(sizeID).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Size with ID " + sizeID + " already exists.");
        }
        try {

            Size newReview = sizeRepository.save(size);
            return ResponseEntity.status(HttpStatus.CREATED).body(newReview);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating product.");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Size> updateSize(@PathVariable("id") int id, @Valid @RequestBody Size size) {
        Size updateSize = sizeService.updateSize(id, size);

        if (updateSize == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity.ok(updateSize);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSize(@PathVariable("id") int id) {
        Size review = sizeRepository.findById(id).orElse(null);
        if (review != null) {
            sizeService.deleteSize(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

