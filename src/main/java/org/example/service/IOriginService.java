package org.example.service;

import org.example.entity.Origin;

import java.util.List;

public interface IOriginService {
    Origin updateOrigin(int id, Origin origin);

    void deleteOrigin(int id);

    List<Origin> findAllEnable();
}