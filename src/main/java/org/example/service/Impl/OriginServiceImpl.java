package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.News;
import org.example.entity.Origin;
import org.example.entity.enums.Status;
import org.example.repository.OriginRepository;
import org.example.service.IOriginService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OriginServiceImpl implements IOriginService {
    private final OriginRepository originRepository;
    @Override
    public Origin updateOrigin(int id, Origin origin) {
        Origin newOrigin = originRepository.findById(id).orElse(null);
        if (newOrigin!=null){
            newOrigin.setCountry(origin.getCountry());
            newOrigin.setStatus(origin.getStatus());

            return originRepository.save(newOrigin);
        }
        return null;
    }

    @Override
    public void deleteOrigin(int id) {
        Origin origin = originRepository.findById(id).orElse(null);
        assert origin != null;
        origin.setStatus(Status.Disable);
        originRepository.save(origin);
    }

    @Override
    public List<Origin> findAllEnable() {
        return originRepository.findAllByStatus(Status.Enable);
    }
}

