package freek.anotheratempt.Servicies.Impl;

import freek.anotheratempt.DTO.Filter;
import freek.anotheratempt.Entities.PlayGround;
import freek.anotheratempt.Repo.PlayGroundRepo;
import freek.anotheratempt.Servicies.PlayGroundService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PlayGroundServiceImpl implements PlayGroundService {

    @Value("${upload.path}")
    private String path;

    private final PlayGroundRepo playGroundRepo;

    public PlayGroundServiceImpl(PlayGroundRepo playGroundRepo) {
        this.playGroundRepo = playGroundRepo;
    }


    @Override
    public List<String> getAllNames() {return playGroundRepo.getDistinctNames();}
    @Override
    @Transactional(readOnly = true)
    public List<Integer> getAllAreas() {return playGroundRepo.getDistinctAreas();}
    @Override
    @Transactional(readOnly = true)
    public List<Integer> getAllPrices() {return playGroundRepo.getDistinctPrices();}
    @Override
    @Transactional(readOnly = true)
    public List<Integer> getAllMaxPeople() {return playGroundRepo.getDistinctMaxPeople();}
    @Override
    @Transactional(readOnly = true)
    public List<String> getAllDates() {return playGroundRepo.getDistinctDates();}

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllTimes() {return playGroundRepo.getDistinctTimes();}

    @Override
    @Transactional(readOnly = true)
    public Iterable<PlayGround> getAll() {return playGroundRepo.findAll();}

    @Override
    @Transactional(readOnly = true)
    public PlayGround getPlayGroundById(Long id) {return playGroundRepo.getPlayGroundById(id);}

    @Override
    @Transactional
    public void deletePlayGroundById(Long pgID) {playGroundRepo.deleteById(pgID);}

    @Override
    public boolean addPlayGround(PlayGround pg, MultipartFile img) throws NoSuchAlgorithmException {
        if(playGroundRepo.existsByName(pg.getName())){
            return false;
        }
        playGroundRepo.save(pg);
        if(!img.isEmpty()) {
            try {
                img.transferTo(new File(path + "/pgImages/"+ pg.getName() + pg.getPrice()
                        + "." + Objects.requireNonNull(img.getOriginalFilename()).split("\\.")[1]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public List<PlayGround> filterPG(Filter filter) {
        List<PlayGround> filteredPg = playGroundRepo.findAll();
        if(filter.name != null && !filter.name.isEmpty()) {
            filteredPg = filteredPg.stream().filter(f -> f.getName().contains(filter.name)).toList();
        }

        if(filter.times != null && filter.times.size() != 0) {
            filteredPg = filteredPg
                    .stream()
                    .filter(
                            f -> f.times.stream()
                                    .filter(filter.times::contains)
                                    .collect(Collectors.toSet()).size() != 0)
                    .toList();
        }
        if(filter.dates != null && filter.dates.size() != 0) {
            filteredPg = filteredPg
                    .stream()
                    .filter(
                            f-> f.dates.stream()
                                    .filter(filter.dates::contains)
                                    .collect(Collectors.toSet()).size() != 0)
                    .toList();

        }
        if (filter.minPrice != -1) {
            filteredPg = filteredPg
                    .stream()
                    .filter(
                            f->f.getPrice() >= filter.minPrice
                    ).toList();
        }
        if (filter.maxPrice != -1) {
            filteredPg = filteredPg
                    .stream()
                    .filter(
                            f->f.getPrice() <= filter.maxPrice
                    ).toList();
        }
        if (filter.minArea != -1) {
            filteredPg = filteredPg
                    .stream()
                    .filter(
                            f->f.getArea() >= filter.minArea
                    ).toList();
        }
        if (filter.maxArea != -1) {
            filteredPg = filteredPg
                    .stream()
                    .filter(
                            f->f.getArea() <= filter.maxArea
                    ).toList();
        }
        if (filter.minPeople != -1) {
            filteredPg = filteredPg
                    .stream()
                    .filter(
                            f->f.getMaxPeople() >= filter.minPeople
                    ).toList();
        }

        return filteredPg;
    }

    @Override
    public File getPlayGroundImage(String playGroundName) {
        File f = new File(path + "/pgImages/");
        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(playGroundName);
            }
        });

        return matchingFiles[0];
    }

    @Override
    public void saveChanges(PlayGround playGround) {
        PlayGround pgInDb = playGroundRepo.findById(playGround.getId()).get();
        pgInDb.setName(playGround.getName());
        pgInDb.setDescription(playGround.getDescription());
        pgInDb.setPrice(playGround.getPrice());
        pgInDb.setArea(playGround.getArea());
        pgInDb.setMaxPeople(playGround.getMaxPeople());
        pgInDb.setDates(playGround.getDates());
        pgInDb.setTimes(playGround.getTimes());

        playGroundRepo.save(pgInDb);

    }
}
