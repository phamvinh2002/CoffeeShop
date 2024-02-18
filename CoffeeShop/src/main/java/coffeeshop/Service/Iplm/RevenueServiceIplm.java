package coffeeshop.Service.Iplm;

import coffeeshop.Entity.OrderDetailEntity;
import coffeeshop.Entity.OrderEntity;
import coffeeshop.Repository.OrderDatailRepository;
import coffeeshop.Repository.OrderRepository;
import coffeeshop.Service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RevenueServiceIplm implements RevenueService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDatailRepository  orderDatailRepository;

    public List<Double> getTotalRevenueByYear(int year) {
        List<Double> monthlyRevenueList = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            double totalRevenue = getTotalRevenueByMonth(year, month);
            monthlyRevenueList.add(totalRevenue);
        }
        return monthlyRevenueList;
    }

    private double getTotalRevenueByMonth(int year, int month) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1,0,0);
        LocalDateTime endDate = startDate.plusMonths(1).minusDays(1);
        List<OrderEntity> listOrder = orderRepository.findAllByOrderDateBetween(startDate, endDate);
        if (listOrder.isEmpty()){
            return 0;
        } else {
            double totalRevenue = 0;
            for (OrderEntity order : listOrder) {
                List<OrderDetailEntity> listOrderDetail = orderDatailRepository.findAllByOrderEntity_Id(order.getId());
                double totalOrder = 0;
                for (OrderDetailEntity orderDetail : listOrderDetail) {
                    double total = orderDetail.getPrice() * orderDetail.getQuantity();
                    totalOrder = totalOrder + total;
                }
                totalRevenue = totalRevenue + totalOrder;
            }
            return totalRevenue;
        }
    }
}
