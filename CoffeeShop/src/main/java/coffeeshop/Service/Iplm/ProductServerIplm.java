package coffeeshop.Service.Iplm;

import coffeeshop.Entity.ProductEntity;
import coffeeshop.Repository.ProductRepository;
import coffeeshop.Service.ProductServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;


@Service
public class ProductServerIplm implements ProductServer {
    public static final String UPLOAD_DIRECTORY = System.getProperty("user.dir")+"/src/main/upload/ProductImage/";

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<ProductEntity> findNameContaining(String search, Pageable pageable) {
        return productRepository.findAllByProductnameContaining(search,pageable);
    }

    @Override
    public Page<ProductEntity> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }



    @Override
    public ProductEntity findById(Long id) {
        return productRepository.getReferenceById(id);
    }

    @Override
    public ProductEntity saveProduct(ProductEntity product){
        return productRepository.save(product);
    }

    @Override
    public String uploadImage(MultipartFile image) throws IOException {
        Path uploadPath = Path.of(UPLOAD_DIRECTORY);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(image.getOriginalFilename());
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString();
    }


}
