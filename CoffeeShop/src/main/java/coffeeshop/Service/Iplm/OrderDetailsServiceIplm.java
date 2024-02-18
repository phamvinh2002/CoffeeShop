package coffeeshop.Service.Iplm;


import coffeeshop.Entity.OrderDetailEntity;
import coffeeshop.Repository.OrderDatailRepository;
import coffeeshop.Service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsServiceIplm implements OrderDetailsService {
    @Autowired
    private OrderDatailRepository datailRepository;
    @Override
    public List<OrderDetailEntity> findOrderDetailByOrderId(Long id) {
        return datailRepository.findAllByOrderEntity_Id(id);
    }
}
