package org.example.service;

import org.example.entity.Size;

public interface ISizeService {
    Size updateSize(int id, Size size);

    void deleteSize(int id);
}