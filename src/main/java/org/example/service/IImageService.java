package org.example.service;

import org.example.entity.Image;

import java.util.List;

public interface IImageService {
    List<Image> getAllImages();
    Image getImageById(Integer id);
    Image createImage(Image image);
    Image updateImage(Integer id, Image imageDetails);
    void deleteImage(Integer id);
    void disableImage(Integer id);

}
