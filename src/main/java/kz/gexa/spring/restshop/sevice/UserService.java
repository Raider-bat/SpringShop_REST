package kz.gexa.spring.restshop.sevice;


import kz.gexa.spring.restshop.dto.UpdateUserPassDto;
import kz.gexa.spring.restshop.dto.UserDto;
import kz.gexa.spring.restshop.entity.user.Role;
import kz.gexa.spring.restshop.entity.user.User;
import kz.gexa.spring.restshop.repository.user.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final
    UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).get();
    }

    public Optional<User> getUserById(Long id){
         return userRepo.findById(id);
    }

    @Transactional
    public void deleteUserById(Long id) throws Exception {
        Long userDelCount = userRepo.deleteUserById(id);
        if (userDelCount ==0){
            throw new Exception("User not found");
        }
    }

    public void createUser(UserDto userDto) throws Exception {
        User user = userDto.toUser();
        user.setPassword(userDto.getPassword());
        Optional<User> userOptional = userRepo.findByUsername(user.getUsername());
        if(userOptional.isPresent()){
            throw new Exception("User is exist");
        }
        if (
                user.getUsername().length() > 3 &&
                        user.getUsername().length() < 20 &&
                        user.getPassword().length() > 6 &&
                        user.getPassword().length() < 61
        ){
            user.setActive(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
        }else {
            throw new Exception("Invalid data");
        }
    }

    public void updateUserPasswordByName(UpdateUserPassDto userPassDto) throws Exception {
        Optional<User> userOptional = userRepo.findByUsername(userPassDto.getUsername());
        if (
                userPassDto.getNewPassword().length() >5 &&
                userPassDto.getNewPassword().length() < 61
        ){
            if (userOptional.isPresent()){
                User user = userOptional.get();
                String hashNewPass = passwordEncoder.encode(userPassDto.getNewPassword());
                user.setPassword(hashNewPass);
                userRepo.save(user);
            }else {
                throw new Exception("User not Found");
            }
        }else {
            throw new Exception("Invalid New Password");
        }
    }



    public boolean isAdmin(String username){
        boolean isAdmin = false;

        if (username != null){
            User user = userRepo.findByUsername(username).get();
            for (Role role:
                    user.getRoles() ) {
                if (role == Role.ADMIN) {
                    isAdmin = true;
                    break;
                }
            }
        }
        return isAdmin;
    }
}