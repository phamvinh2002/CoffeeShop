package coffeeshop.Controller;


import coffeeshop.Entity.QrCodeEntity;
import coffeeshop.Service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping()
public class QrCodeController {

    @Autowired
    private QrCodeService qrCodeService;

    @PostMapping("generateQR")
    public ResponseEntity<RedirectView> generateQRCode(@RequestParam("index") String index) {
        QrCodeEntity existQRCode = qrCodeService.findByTableIndex(index);
        if (existQRCode != null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            String content = "http://localhost:5000/" + index + "/menu";
            qrCodeService.generateQrCodeAndSave(content, index);
            String url = "/viewQRCode";
            RedirectView redirectView = new RedirectView(url);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
    }

    @GetMapping("viewQRCode")
    public String listQR(Model model) {
        model.addAttribute("listQRCode", qrCodeService.findAll());
        return "QRCode/viewQrCode";
    }

}
