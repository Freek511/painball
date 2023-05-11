package freek.anotheratempt.Servicies.Impl;

import freek.anotheratempt.Configs.SecurityConfig;
import freek.anotheratempt.Entities.Enums.Role;
import freek.anotheratempt.Entities.User;
import freek.anotheratempt.Repo.UserRepo;
import freek.anotheratempt.Servicies.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean addUser(User user) {
        if(userRepo.getByEmail(user.getEmail()) != null) {
            return false;
        }
        if(user.getPassword() == null) {
            return false;
        }
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepo.save(user);
        return true;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.getByEmail(username);
    }
}
