package freek.anotheratempt.Repo;

import freek.anotheratempt.Entities.Order;
import freek.anotheratempt.Entities.PlayGround;
import freek.anotheratempt.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    @Query(value = "SELECT o from Order o where o.playGround.Id in :ids and o.user = :user")
    List<Order> findAllByListIds(@Param("ids") List<Long> ids, @Param("user") User user);

    List<Order> findAllByUser(User user);
}
