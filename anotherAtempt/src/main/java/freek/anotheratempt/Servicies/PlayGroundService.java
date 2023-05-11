package freek.anotheratempt.Servicies;

import freek.anotheratempt.DTO.Filter;
import freek.anotheratempt.Entities.PlayGround;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface PlayGroundService {

    Iterable<PlayGround> getAll();
    public PlayGround getPlayGroundById(Long id);

    void deletePlayGroundById(Long pgID);
    boolean addPlayGround(PlayGround pg, MultipartFile img) throws NoSuchAlgorithmException;


    List<String> getAllNames();
    List<Integer> getAllAreas();
    List<Integer> getAllPrices();
    List<Integer> getAllMaxPeople();
    List<String> getAllDates();
    List<String> getAllTimes();
    List<PlayGround> filterPG(Filter filter);

    File getPlayGroundImage(String playGroundName);

    void saveChanges(PlayGround playGround);

}
