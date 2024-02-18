package coffeeshop.Service;

import coffeeshop.Entity.UserEntity;

public interface UserService {

    UserEntity findUser(String username);
}
