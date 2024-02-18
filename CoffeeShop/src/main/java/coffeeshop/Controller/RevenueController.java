package coffeeshop.Controller;


import coffeeshop.Service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    @GetMapping("/revenue/year")
    public List<Double> getTotalRevenueByYear(@RequestParam("year") int year) {
        return revenueService.getTotalRevenueByYear(year);
    }
}
