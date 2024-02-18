package coffeeshop.Controller;


import coffeeshop.Entity.CartEntity;
import coffeeshop.Entity.ProductEntity;
import coffeeshop.Service.ProductServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/{index}/menu")
public class ProcductController {

    @Autowired
    private ProductServer productServer;

    @Autowired
    private CartEntity cartEntity;

    @GetMapping
    public String listProduct(Model model, @RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "1") int page, @PathVariable("index") int index) {

        Pageable pageable = PageRequest.of(page-1,15);

        Page<ProductEntity> listProduct = productServer.findNameContaining(search, pageable);

        model.addAttribute("products", listProduct);
        model.addAttribute("search", search);
        model.addAttribute("index",index);
        model.addAttribute("itemquantity", cartEntity.count());
        return "Menu/menu";
    }




}
