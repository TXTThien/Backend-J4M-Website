package org.example.service;

import org.example.entity.Origin;

public interface IOriginService {
    Origin updateOrigin(int id, Origin origin);

    void deleteOrigin(int id);
}