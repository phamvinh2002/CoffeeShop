package coffeeshop.Controller;


import coffeeshop.Service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class QrCodeController {

    @Autowired
    private QrCodeService qrCodeService;

    @PostMapping("generateQR")
    public String generateQRCode(@ModelAttribute("indextable") String index) {
        String content = "http://localhost:8080/"+index+"/menu";
        qrCodeService.generateQrCodeAndSave(content,index);
        return "redirect:viewQRCode";
    }

    @GetMapping("viewQRCode")
    public String listQR(Model model) {
        model.addAttribute("listQRCode", qrCodeService.findAll());
        return "QRCode/viewqrcode";
    }

}
