package coffeeshop.Entity;


import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private int tableIndex;


    private LocalDateTime orderDate;

    private int paymentStatus;

    private int serveStatus;

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getServeStatus() {
        return serveStatus;
    }

    public void setServeStatus(int serveStatus) {
        this.serveStatus = serveStatus;
    }

    @OneToMany(mappedBy = "orderEntity", fetch = FetchType.EAGER)
    private List<OrderDetailEntity> orderDetailsEntities = new ArrayList<>();

    public OrderEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(int tableIndex) {
        this.tableIndex = tableIndex;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }


    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderDetailEntity> getOrderDetailsEntities() {
        return orderDetailsEntities;
    }

    public void setOrderDetailsEntities(List<OrderDetailEntity> orderDetailsEntities) {
        this.orderDetailsEntities = orderDetailsEntities;
    }
}
