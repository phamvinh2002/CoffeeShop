package coffeeshop.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RevenuePageController {

    @GetMapping("/revenue")
    public String revenuePagr(){
        return "Revenue/RevenuePage";
    }
}
