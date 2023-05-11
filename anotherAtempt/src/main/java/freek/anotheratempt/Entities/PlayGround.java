package freek.anotheratempt.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "playgrounds")
@Getter
@Setter
public class PlayGround {

    @Id
    @SequenceGenerator(name = "playgrounds_seq",sequenceName =
            "playgrounds_sequence", allocationSize = 1)
    @GeneratedValue(generator = "playgrounds_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long Id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "area")
    private int area;

    @Column(name = "maxpeople")
    private int maxPeople;

    @ElementCollection
    public List<String> dates;
    @ElementCollection
    public List<String> times;


    @Override
    public String toString() {
        return "PlayGround{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", area=" + area +
                ", maxPeople=" + maxPeople +
                ", dates=" + dates +
                ", times=" + times +
                '}';
    }

    public PlayGround(){}

}
