package coffeeshop.Controller;

import coffeeshop.Entity.ProductEntity;
import coffeeshop.Service.ProductServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
@RequestMapping("/product")
public class ProductManagerController {

    @Autowired
    private ProductServer productServer;

    @GetMapping()
    public String productManager(Model model, @RequestParam (defaultValue = "1") int page){
        Pageable pageable = PageRequest.of(page-1,10);
        Page<ProductEntity> listProduct = productServer.findAll(pageable);

       model.addAttribute("products",listProduct);

        return "productManager/ListProduct";
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("productID") Long id){
        productServer.deleteById(id);
        return "redirect:/product";
    }

    @GetMapping("/update/{id}")
    public String updatePage(Model model, @PathVariable Long id){
        ProductEntity product = productServer.findById(id);
        model.addAttribute("product",product);
        return "productManager/updateProduct";
    }

    @PutMapping("/update")
    public ResponseEntity<RedirectView> updateProduct(@RequestBody ProductEntity product){
        ProductEntity existProduct = productServer.findById(product.getId());
        if (existProduct != null){
            existProduct.setProductname(product.getProductname());
            existProduct.setPrice(product.getPrice());
            existProduct.setProducttype(product.getProducttype());
        }
        productServer.saveProduct(existProduct);

        String url = "/product";
        RedirectView redirectView = new RedirectView(url);
        return new ResponseEntity<>(redirectView, HttpStatus.OK);
    }

    @GetMapping("/create")
    public String createPage(){
        return "productManager/createProduct";
    }


    @PostMapping("/create")
    public ResponseEntity<RedirectView> createProduct(@RequestParam("name") String name, @RequestParam("price") double price,
                                @RequestParam("type") String type, @RequestParam("image")MultipartFile image ) throws IOException {

            ProductEntity product = new ProductEntity();
            product.setProductname(name);
            product.setPrice(price);
            product.setProducttype(type);
            String fullUrl = productServer.uploadImage(image);
            int indexOfImage = fullUrl.indexOf("\\upload");
            String imageUrl = null;
            if (indexOfImage != -1) {
                imageUrl = fullUrl.substring(indexOfImage);
            }
            product.setUrlimage(imageUrl);

            productServer.saveProduct(product);
        String url = "/product";
        RedirectView redirectView = new RedirectView(url);
        return new ResponseEntity<>(redirectView, HttpStatus.OK);
    }
}


