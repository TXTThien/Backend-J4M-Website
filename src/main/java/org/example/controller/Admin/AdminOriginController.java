package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.repository.AccountRepository;
import org.example.repository.OriginRepository;
import org.example.service.Impl.AccountServiceImpl;
import org.example.service.Impl.OriginServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/origin")
@RequiredArgsConstructor
public class AdminOriginController {
    private final OriginRepository originRepository;
    private final OriginServiceImpl originService;
}
