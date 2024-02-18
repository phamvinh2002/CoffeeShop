package coffeeshop.Controller;

import coffeeshop.Entity.CartEntity;
import coffeeshop.Entity.ProductEntity;
import coffeeshop.Service.ProductServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("{index}/cart")
public class CartController {

    @Autowired
    private CartEntity cartEntity;

    @Autowired
    private ProductServer productServer;

    @PostMapping("add")
    @ResponseBody
    public ResponseEntity<?> add(@ModelAttribute("pro") ProductEntity product){
        cartEntity.addItem(product);
        Map<String, Object> res = new HashMap<>();
        res.put("numberOfItems", cartEntity.count());
        return ResponseEntity.ok(res);
    }

    @GetMapping("view")
    public String showCart(Model model,@PathVariable("index") int index) {
        model.addAttribute("carts", cartEntity.getAll());
        model.addAttribute("numberOfItems", cartEntity.count());
        model.addAttribute("total",cartEntity.total());
        model.addAttribute("index",index);
        return "Cart/ShowCart";
    }

    @PostMapping("increase")
    public String increase(@ModelAttribute("pro") ProductEntity product, @PathVariable("index") int index){
        cartEntity.addItem(product);
        return "redirect:/"+index+"/cart/view";
    }

    @PostMapping("decrease")
    public String decrease(@ModelAttribute("pro") ProductEntity product, @PathVariable("index") int index){
        cartEntity.removeItem(product);
        return "redirect:/"+index+"/cart/view";
    }

    @PostMapping("clear")
    public String clearCart(@PathVariable("index") int index){
        cartEntity.emptyCart();
        return "redirect:/"+index+"/cart/view";
    }
}
