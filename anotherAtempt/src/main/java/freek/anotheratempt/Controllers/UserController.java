package freek.anotheratempt.Controllers;

import freek.anotheratempt.DTO.CreateUserDto;
import freek.anotheratempt.Entities.User;
import freek.anotheratempt.Mappers.UserMapper;
import freek.anotheratempt.Servicies.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserMapper _userMapper = Mappers.getMapper(UserMapper.class);
    private final UserService _userService;

    public UserController(UserService service) {
        _userService = service;
    }

    @GetMapping("/registration")
    public String registrationGet() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(CreateUserDto createUserDto) {
        User user = _userMapper.createUserDtoToUser(createUserDto);
        if(!_userService.addUser(user)) {
            return "registration";
        }
        return "redirect:/login";
    }


}
