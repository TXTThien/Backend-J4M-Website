package org.example.service.Impl;

import org.example.entity.Image;
import org.example.repository.ImageRepository;
import org.example.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.entity.enums.Status;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements IImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public Image getImageById(Integer id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public Image createImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Image updateImage(Integer id, Image imageDetails) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();

            // Cập nhật URL của hình ảnh
            image.setImageURL(imageDetails.getImageURL());

            // Cập nhật sản phẩm, đảm bảo không null
            if (imageDetails.getProduct() != null) {
                image.setProduct(imageDetails.getProduct()); // Sử dụng setProduct
            }

            // Cập nhật trạng thái
            if (imageDetails.getStatus() != null) {
                image.setStatus(imageDetails.getStatus());
            }

            return imageRepository.save(image);
        } else {
            return null;
        }
    }


    @Override
    public void deleteImage(Integer id) {
        imageRepository.deleteById(id);
    }

    @Override
    public void disableImage(Integer id) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();
            image.setStatus(Status.Disable); // Chuyển trạng thái thành Disable
            imageRepository.save(image); // Lưu thay đổi
        }
    }
}
