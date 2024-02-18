package coffeeshop.Repository;

import coffeeshop.Entity.OrderEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByTableIndexAndServeStatus(int tableIndex, int servestatus);

    List<OrderEntity> findAllByTableIndex(int tableIndex);

    @Transactional
    void deleteById(Long id);

    List<OrderEntity> findAllByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
