package freek.anotheratempt.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Long id;

    @OneToOne
    private PlayGround playGround;

    @OneToOne
    private User user;

    @Column(name = "date")
    private String date;

    @Column(name = "time")
    private String time;
}
