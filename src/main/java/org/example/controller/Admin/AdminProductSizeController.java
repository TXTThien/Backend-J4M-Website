package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.repository.AccountRepository;
import org.example.repository.ProductSizeRepository;
import org.example.repository.ProductTypeRepository;
import org.example.service.Impl.AccountServiceImpl;
import org.example.service.Impl.ProductSizeServiceImpl;
import org.example.service.Impl.ProductTypeServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/productsize")
@RequiredArgsConstructor
public class AdminProductSizeController {
    private final ProductSizeRepository productSizeRepository;
    private final ProductSizeServiceImpl productSizeService;
}