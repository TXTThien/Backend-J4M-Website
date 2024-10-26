package org.example.service;

import org.example.entity.Size;

import java.util.List;

public interface ISizeService {
    Size updateSize(int id, Size size);

    void deleteSize(int id);

    List<Size> findAllEnable();
}
