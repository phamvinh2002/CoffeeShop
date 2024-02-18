package coffeeshop.Service;

import coffeeshop.Entity.OrderDetailEntity;

import java.util.List;

public interface OrderDetailsService {
    public List<OrderDetailEntity> findOrderDetailByOrderId(Long id);
}
