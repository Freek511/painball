package freek.anotheratempt.Repo;

import freek.anotheratempt.Entities.PlayGround;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayGroundRepo extends JpaRepository<PlayGround, Long> {
    boolean existsByName(String name);

    PlayGround getPlayGroundById(Long id);

    @Query(value= "SELECT DISTINCT pg.dates FROM PlayGround pg")
    List<String> getDistinctDates();
    @Query(value= "SELECT DISTINCT pg.times FROM PlayGround pg")
    List<String> getDistinctTimes();
    @Query(value = "SELECT distinct pg.name from PlayGround pg")
    List<String> getDistinctNames();
    @Query(value = "SELECT distinct pg.price from PlayGround pg")
    List<Integer> getDistinctPrices();
    @Query(value = "SELECT distinct pg.area from PlayGround pg")
    List<Integer> getDistinctAreas();
    @Query(value = "SELECT distinct pg.maxPeople from PlayGround pg")
    List<Integer> getDistinctMaxPeople();


}
