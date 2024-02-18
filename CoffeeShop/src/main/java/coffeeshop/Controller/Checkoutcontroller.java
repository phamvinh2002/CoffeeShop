package coffeeshop.Controller;

import coffeeshop.Entity.CartEntity;
import coffeeshop.Entity.CartItem;
import coffeeshop.Entity.OrderDetailEntity;
import coffeeshop.Entity.OrderEntity;
import coffeeshop.PayOs.PayOS;
import coffeeshop.Repository.OrderDatailRepository;
import coffeeshop.Repository.OrderRepository;
import coffeeshop.PaymentData.ItemData;
import coffeeshop.PaymentData.PaymentData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class Checkoutcontroller {
    private PayOS payOS;

    public Checkoutcontroller(PayOS payOS) {
        this.payOS = payOS;
    }

    @Autowired
    private CartEntity cartEntity;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDatailRepository orderDatailRepository;


    @RequestMapping(value = "{index}/success")
    public String Success(@PathVariable("index") int index) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setTableIndex(index);
        orderEntity.setPaymentStatus(1);
        orderEntity.setOrderDate(LocalDateTime.now());
        orderEntity.setServeStatus(0);
        orderEntity = orderRepository.save(orderEntity);
        List<OrderDetailEntity> orderDetailList = new ArrayList<>();
        for(CartItem cartItem : cartEntity.getAll()) {
            OrderDetailEntity orderDetail = new OrderDetailEntity();
            orderDetail.setOrderEntity(orderEntity);
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getProduct().getPrice());
            orderDetailList.add(orderDetail);
        }
        orderDatailRepository.saveAll(orderDetailList);

        messagingTemplate.convertAndSend("/topic/sendData",index);

        cartEntity.emptyCart();

        return "redirect:/"+index+"/menu";
    }

    @RequestMapping(value = "{index}/cancel")
    public String Cancel(@PathVariable("index") int index) {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setTableIndex(index);
        orderEntity.setPaymentStatus(0);
        orderEntity.setOrderDate(LocalDateTime.now());
        orderEntity.setServeStatus(0);
        orderEntity = orderRepository.save(orderEntity);
        List<OrderDetailEntity> orderDetailList = new ArrayList<>();
        for(CartItem cartItem : cartEntity.getAll()) {
            OrderDetailEntity orderDetail = new OrderDetailEntity();
            orderDetail.setOrderEntity(orderEntity);
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getProduct().getPrice());
            orderDetail.setProductEntity(cartItem.getProduct());
            orderDetailList.add(orderDetail);
        }
        orderDatailRepository.saveAll(orderDetailList);

        messagingTemplate.convertAndSend("/topic/sendData",index);

        cartEntity.emptyCart();
        return "redirect:/"+index+"/menu";
    }

    @RequestMapping(method = RequestMethod.POST, value = "{index}/create-payment-link", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void checkout(HttpServletResponse httpServletResponse, @PathVariable("index") int index) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final String productName = "Thanh toán hóa đơn bàn số "+index+" tại VHCoffee";
            final String description = "Bàn số "+index+" "+cartEntity.total();
            final String returnUrl = "http://localhost:5000/"+index+"/success";
            final String cancelUrl = "http://localhost:5000/"+index+"/cancel";
            final int price = 2000;
            // Gen order code
            String currentTimeString = String.valueOf(new Date().getTime());
            int orderCode = Integer.parseInt(currentTimeString.substring(currentTimeString.length() - 6));
            ItemData item = new ItemData(productName,1,price);
            List<ItemData> itemList = new ArrayList<>();
            itemList.add(item);
            PaymentData paymentData = new PaymentData(orderCode, price, description,
                    itemList, cancelUrl, returnUrl);
            JsonNode data = payOS.createPaymentLink(paymentData);

            String checkoutUrl = data.get("checkoutUrl").asText();

            httpServletResponse.setHeader("Location", checkoutUrl);
            httpServletResponse.setStatus(302);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
