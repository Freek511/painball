package freek.anotheratempt.Servicies;

import freek.anotheratempt.Entities.Order;
import freek.anotheratempt.Entities.PlayGround;
import freek.anotheratempt.Entities.User;

import java.util.List;

public interface OrderService {
    boolean createOrder(User user, Long pgid, String date, String time);
    void deleteOrder(Long orderId);

    boolean acceptOrder(List<Long> ids, User user);
    public List<Order> getOrderByUser(User user);
}
