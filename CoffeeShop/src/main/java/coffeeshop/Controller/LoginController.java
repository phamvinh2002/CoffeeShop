package coffeeshop.Controller;

import coffeeshop.Entity.UserEntity;
import coffeeshop.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Objects;

@Controller
@RequestMapping
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String loginPage(){
        return "Login/Login";
    }

    @PostMapping("/perform_login")
    public String Login(@RequestParam("username") String username, @RequestParam("password") String password){
        boolean validateUser = Objects.equals(password,
                                userService.findUser(username).getPassword());
        if (validateUser){
            return "redirect:/homepage";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/perform_logout")
    String logout() {
        return "redirect:/";
    }
}
