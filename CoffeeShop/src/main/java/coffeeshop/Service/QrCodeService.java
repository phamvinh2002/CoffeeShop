package coffeeshop.Service;

import coffeeshop.Entity.QrCodeEntity;

import java.util.List;

public interface QrCodeService {

     QrCodeEntity generateQrCodeAndSave(String content, String tableindex);


    List<QrCodeEntity> findAll();

    QrCodeEntity findByTableIndex(String index);
}
