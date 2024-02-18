package coffeeshop.Service;

import coffeeshop.Entity.OrderEntity;

import java.util.List;

public interface OrderService {
    public void save(OrderEntity order);

    public void deleteById(OrderEntity order);
    public List<OrderEntity> findByTableIndexAndServeStatus(int tableIndex, int PaymentStatus);

    List<OrderEntity> findByTableIndex(int tableIndex);
}
