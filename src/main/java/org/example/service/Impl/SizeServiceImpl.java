package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.News;
import org.example.entity.Size;
import org.example.entity.enums.Status;
import org.example.repository.SizeRepository;
import org.example.service.ISizeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements ISizeService {
    private final SizeRepository sizeRepository;
    @Override
    public Size updateSize(int id, Size size) {
        Size newSize = sizeRepository.findById(id).orElse(null);
        if (newSize!=null){
            newSize.setSizeName(size.getSizeName());
            newSize.setBonus(size.getBonus());
            newSize.setStatus(size.getStatus());

            return sizeRepository.save(newSize);
        }
        return null;
    }

    @Override
    public void deleteSize(int id) {
        Size size = sizeRepository.findById(id).orElse(null);
        assert size != null;
        size.setStatus(Status.Disable);
        sizeRepository.save(size);
    }
}