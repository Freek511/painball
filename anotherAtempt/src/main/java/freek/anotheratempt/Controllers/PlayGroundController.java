package freek.anotheratempt.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import freek.anotheratempt.DTO.Filter;
import freek.anotheratempt.Entities.User;
import freek.anotheratempt.Mappers.PlayGroundMapper;
//import freek.anotheratempt.Servicies.OrderService;
import freek.anotheratempt.Servicies.OrderService;
import freek.anotheratempt.Servicies.PlayGroundService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@Controller
@RequestMapping("/playgrounds")
public class PlayGroundController {
    @Value("${upload.path}")
    private String path;
    private final PlayGroundService _playGroundService;

    private final OrderService orderService;
    private final PlayGroundMapper playGroundMapper = Mappers.getMapper(PlayGroundMapper.class);

    public PlayGroundController(PlayGroundService playGroundService, OrderService orderService) {
        _playGroundService = playGroundService;
        this.orderService = orderService;
    }


    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("allDates", _playGroundService.getAllDates());
        model.addAttribute("allTimes", _playGroundService.getAllTimes());
        model.addAttribute("allPgs", playGroundMapper.
                arrayPgToArrayReadDto(_playGroundService.getAll()));
        model.addAttribute("path", path);
        return "all-play-grounds";

    }

    @PostMapping(value = "/all/filtered", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getFilteredList(@RequestBody Filter filter, Model model) throws JsonProcessingException {
        model.addAttribute("allDates", _playGroundService.getAllDates());
        model.addAttribute("allTimes", _playGroundService.getAllTimes());
        model.addAttribute("allPgs", playGroundMapper.
                arrayPgToArrayReadDto(_playGroundService.filterPG(filter)));
        model.addAttribute("path", path);
        return "helper-list";
    }

    @GetMapping("/image/{name}")
    @ResponseBody
    public byte[] getImage(@PathVariable String name) throws IOException {
        File serverFile = _playGroundService.getPlayGroundImage(name);
        return Files.readAllBytes(serverFile.toPath());
    }

    @GetMapping("/{id}")
    public String getPlayGroundById(@PathVariable Long id, Model model) {
        var check = playGroundMapper.pgToReadpgDto(_playGroundService.getPlayGroundById(id));
        model.addAttribute("pgData", check);
        return "play-ground-page";

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addToOrder/{id}")
    public String addToOrder(@PathVariable Long id, @RequestParam String time,
                             @RequestParam String date, @AuthenticationPrincipal User user) {
        Boolean isGood = false;
        if(user != null) {
            isGood = orderService.createOrder(user, id, time, date);
        }
        return "redirect:/playgrounds/" + id;
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/orders")
    public String getBasket(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("orderData",orderService.getOrderByUser(user));
        return "orders";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/deleteFromOrder/{orderId}")
    public String deleteFromBasket(@AuthenticationPrincipal User user, @PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return "redirect:/playgrounds/orders";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/orders")
    public String acceptOrder(@RequestParam List<Long> ids, @AuthenticationPrincipal User user) {
        orderService.acceptOrder(ids, user);
        return "redirect:/playgrounds/orders";
    }
}