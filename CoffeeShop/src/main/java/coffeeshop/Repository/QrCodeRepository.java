package coffeeshop.Repository;


import coffeeshop.Entity.QrCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCodeEntity, Long> {

    List<QrCodeEntity> findAllByOrderByContentAsc();

    QrCodeEntity findByTableIndex(String tableIndex);
}
