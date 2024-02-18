package coffeeshop.Service.Iplm;

import coffeeshop.Entity.QrCodeEntity;
import coffeeshop.Repository.QrCodeRepository;
import coffeeshop.Service.QrCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class QrCodeServiceIplm implements QrCodeService {
    @Autowired
    private QrCodeRepository qrCodeRepository;


    @Override
    public QrCodeEntity generateQrCodeAndSave(String content, String tableIndex) {
        // Tạo đường dẫn ảnh QR code
        String imgFilePath = generateQRCodeImage(content,tableIndex);
        int indexOfQrCodeImage = imgFilePath.indexOf("\\upload");
        String imageUrl = null;
        if (indexOfQrCodeImage != -1){
           imageUrl = imgFilePath.substring(indexOfQrCodeImage);
        }

        // Lưu dữ liệu QR code vào cơ sở dữ liệu
        QrCodeEntity qrCodeData = new QrCodeEntity();
        qrCodeData.setContent(content);
        qrCodeData.setTableIndex(tableIndex);
        qrCodeData.setImagePath(imageUrl);

        qrCodeRepository.save(qrCodeData);

        return qrCodeData;
    }

    private String generateQRCodeImage(String content, String tableIndex) {
        try {
            // Tạo đối tượng QRCodeWriter
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            // Tạo thông số để cấu hình mã QR
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // Tạo BitMatrix từ nội dung và thông số
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);

            // Tạo đường dẫn đến thư mục lưu ảnh QR code

            Path path = FileSystems.getDefault().getPath("./src/main/upload/QrCodeImage"); //thư muc có tên QR code


            // Kiểm tra và tạo thư mục nếu chưa tồn tại
            if (!path.toFile().exists()) {
                path.toFile().mkdirs();
            }

            // Tạo đường dẫn đến tệp hình ảnh QR code
            Path imageFilePath = FileSystems.getDefault().getPath(String.valueOf(path),"Table"+tableIndex+".png");
            // thay đổi tên file cho phù hợp.

            // Ghi BitMatrix vào tệp hình ảnh
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", imageFilePath);

            // Trả về đường dẫn tệp hình ảnh đã tạo
            return imageFilePath.toString();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu cần thiết
            return null;
        }
    }

    @Override
    public List<QrCodeEntity> findAll() {
        return qrCodeRepository.findAllByOrderByContentAsc();
    }

    @Override
    public QrCodeEntity findByTableIndex(String index) {
        return qrCodeRepository.findByTableIndex(index);
    }

}

