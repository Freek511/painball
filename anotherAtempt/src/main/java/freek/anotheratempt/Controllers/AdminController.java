package freek.anotheratempt.Controllers;

import freek.anotheratempt.DTO.CreatePgDto;
import freek.anotheratempt.Entities.PlayGround;
import freek.anotheratempt.Mappers.PlayGroundMapper;
import freek.anotheratempt.Servicies.PlayGroundService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {

    private final PlayGroundService _playGroundService;
    private final PlayGroundMapper playGroundMapper = Mappers.getMapper(PlayGroundMapper.class);

    @GetMapping("/create_playground")
    public String getCreatePlayGroundPage(Model model) {
        return "create-play-ground";
    }

    @PostMapping(value = "/create_playground")
    public String createCreatePlayGroundPage(@RequestParam String name,
                                             @RequestParam String description,
                                             @RequestParam List<String> times,
                                             @RequestParam List<String> dates,
                                             @RequestParam int price,
                                             @RequestParam int area,
                                             @RequestParam int maxPeople,
                                             @RequestParam MultipartFile img
    ) throws NoSuchAlgorithmException {
        CreatePgDto createPgDto = new CreatePgDto(name,description,price, area,maxPeople, dates, times);
        System.out.println(createPgDto.toString());
        PlayGround playGround = playGroundMapper.createPgDtoToPg(createPgDto);
        System.out.println(playGround.toString());
        if( playGround != null){
            _playGroundService.addPlayGround(playGround, img);
        }
        else {
            return "redirect:/playgrounds/all";
        }
        return "redirect:/playgrounds/all";
    }

    @PostMapping("/delete_page")
    public String DeletePage(@RequestParam Long pgid) {
        _playGroundService.deletePlayGroundById(pgid);
        return "redirect:/playgrounds/all";
    }
    @GetMapping("/change_page")
    public String ChangePage(@RequestParam Long pgid, Model model) {
        model.addAttribute("pgData", _playGroundService.getPlayGroundById(pgid));
        return "edit-play-ground";

    }
    @PostMapping("/change_page")
    public String SaveChanges(PlayGround playGround) {
        _playGroundService.saveChanges(playGround);
        return "redirect:/playgrounds/" + playGround.getId();
    }
}
