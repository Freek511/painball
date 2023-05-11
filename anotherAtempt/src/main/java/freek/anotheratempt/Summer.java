package freek.anotheratempt;

import freek.anotheratempt.Entities.Order;

import java.util.List;

public class Summer {
    public static int sum(List<Order> orders) {
        return orders.stream().mapToInt(o -> o.getPlayGround().getPrice()).sum();
    }
}