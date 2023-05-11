package freek.anotheratempt.Servicies.Impl;

import freek.anotheratempt.Entities.Order;
import freek.anotheratempt.Entities.PlayGround;
import freek.anotheratempt.Entities.User;
import freek.anotheratempt.Repo.OrderRepo;
import freek.anotheratempt.Repo.PlayGroundRepo;
import freek.anotheratempt.Servicies.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final PlayGroundRepo playGroundRepo;

    public OrderServiceImpl(OrderRepo orderRepo, PlayGroundRepo playGroundRepo) {
        this.orderRepo = orderRepo;
        this.playGroundRepo = playGroundRepo;
    }

    @Override
    @Transactional
    public boolean createOrder(User user, Long pgid, String date, String time) {
        PlayGround playGround = playGroundRepo.getPlayGroundById(pgid);
        if(playGround == null){
            return false;
        }
        Order order = new Order();
        order.setUser(user);
        order.setPlayGround(playGround);
        order.setDate(date);
        order.setTime(time);
        orderRepo.save(order);
        return true;
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepo.deleteById(orderId);
    }

    @Override
    @Transactional
    public boolean acceptOrder(List<Long> ids, User user) {
        List<Order> toDelete = orderRepo.findAllByListIds(ids, user);
        List<String> toDeleteTimes;
        List<String> toDeleteDates;
        orderRepo.deleteAll(toDelete);

         // TODO delete time for playground
        for(Order order : toDelete){
            toDeleteTimes = (order.getPlayGround().times);
            toDeleteDates = (order.getPlayGround().dates);
//            System.out.println(playGroundRepo.findAllByTimes(toDeleteTimes));
        }
        return true;
    }

    @Override
    public List<Order> getOrderByUser(User user) {
        return orderRepo.findAllByUser(user);
    }
}
