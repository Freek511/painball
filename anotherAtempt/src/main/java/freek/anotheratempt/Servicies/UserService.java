package freek.anotheratempt.Servicies;

import freek.anotheratempt.Entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    boolean addUser(User user);
}
