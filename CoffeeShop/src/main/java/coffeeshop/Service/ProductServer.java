package coffeeshop.Service;

import coffeeshop.Entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ProductServer {

    Page<ProductEntity> findNameContaining(String search, Pageable pageable);

    Page<ProductEntity> findAll(Pageable pageable);

    void deleteById(Long id);

    ProductEntity findById(Long id);

    ProductEntity saveProduct(ProductEntity product);

    String uploadImage(MultipartFile image) throws IOException;
}
