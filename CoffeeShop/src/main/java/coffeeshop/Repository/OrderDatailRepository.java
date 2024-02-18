package coffeeshop.Repository;

import coffeeshop.Entity.OrderDetailEntity;
import coffeeshop.Entity.OrderEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDatailRepository extends JpaRepository<OrderDetailEntity, Long> {

    List<OrderDetailEntity> findAllByOrderEntity_Id(Long id);

    @Transactional
    void deleteAllByOrderEntity_Id(Long id);
}
