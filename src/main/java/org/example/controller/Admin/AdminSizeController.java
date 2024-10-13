package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.repository.ProductTypeRepository;
import org.example.repository.SizeRepository;
import org.example.service.Impl.ProductTypeServiceImpl;
import org.example.service.Impl.SizeServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/size")
@RequiredArgsConstructor
public class AdminSizeController {
    private final SizeRepository sizeRepository;
    private final SizeServiceImpl sizeService;
}
