package coffeeshop.Service.Iplm;

import coffeeshop.Entity.UserEntity;
import coffeeshop.Repository.UserRepository;
import coffeeshop.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceiplm implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity findUser(String username){
        return userRepository.findAllByUsername(username);
    }

}
