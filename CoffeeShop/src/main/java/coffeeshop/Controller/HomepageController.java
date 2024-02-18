package coffeeshop.Controller;


import coffeeshop.Entity.OrderDetailEntity;
import coffeeshop.Entity.OrderEntity;
import coffeeshop.Entity.QrCodeEntity;
import coffeeshop.Service.OrderDetailsService;
import coffeeshop.Service.OrderService;
import coffeeshop.Service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/homepage")
public class HomepageController {
    @Autowired
    private QrCodeService qrCodeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping()
    private String homepage(Model model){
        List<QrCodeEntity> qrCodeEntityList = qrCodeService.findAll();

        model.addAttribute("listQrCode",qrCodeEntityList);
        return "Management/Homepage";
    }

    @GetMapping("/{index}")
    public String listOrder(Model model, @PathVariable("index") int tableindex){
        List<OrderEntity> listOrderbyIndex = orderService.findByTableIndex(tableindex);
        model.addAttribute("listOrder",listOrderbyIndex);
        model.addAttribute("tableIndex",tableindex);
        return "Management/ListOrder";
    }

    @PostMapping("/{index}/payment")
    public String Payment(@ModelAttribute("orderID") OrderEntity order, @PathVariable int index){
        order.setPaymentStatus(1);
        orderService.save(order);
        return "redirect:/homepage/"+index;
    }

    @PostMapping("/{index}/serve")
    public String Serve(@ModelAttribute("serveID") OrderEntity order, @PathVariable int index){
        order.setServeStatus(1);
        orderService.save(order);
        return "redirect:/homepage/"+index;
    }

    @PostMapping("/{index}/deleteOrder")
    public String DeleteOrder(@ModelAttribute("deleteID") OrderEntity order, @PathVariable int index){
        orderService.deleteById(order);
        return "redirect:/homepage/"+index;
    }

    @PostMapping("/{index}/viewDetail")
    public String listOrderdetail(Model model, @ModelAttribute("detailID") OrderEntity order, @PathVariable int index){
        Long id = order.getId();
        List<OrderDetailEntity> listOrderDetailbyOrderId = orderDetailsService.findOrderDetailByOrderId(id);
        model.addAttribute("listOrderDetail",listOrderDetailbyOrderId);
        model.addAttribute("tableIndex",index);
        return "Management/ListOrderDetail";
    }
}
