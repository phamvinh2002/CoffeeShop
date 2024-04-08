package coffeeshop.Service.Iplm;


import coffeeshop.Entity.OrderEntity;
import coffeeshop.Repository.OrderDatailRepository;
import coffeeshop.Repository.OrderRepository;
import coffeeshop.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceiplm implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDatailRepository orderDatailRepository;

    @Transactional
    @Override
    public void save(OrderEntity order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteById(OrderEntity order) {
        orderDatailRepository.deleteAllByOrderEntity_Id(order.getId());
        orderRepository.deleteById(order.getId());
    }

    @Override
    public List<OrderEntity> findByTableIndexAndServeStatus(int tableIndex, int ServeStatus) {
        return orderRepository.findAllByTableIndexAndServeStatus(tableIndex, ServeStatus);
    }

    @Override
    public List<OrderEntity> findByTableIndex(int tableIndex) {
        return orderRepository.findAllByTableIndex(tableIndex);
    }

}
