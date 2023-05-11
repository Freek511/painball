package freek.anotheratempt.DTO;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CreatePgDto {
    public String name;

    public String description;

    public int price;

    public int area;

    public int maxPeople;

    public List<String> dates;

    public List<String> times;

    @Override
    public String toString() {
        return "CreatePgDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", area=" + area +
                ", maxPeople=" + maxPeople +
                ", dates=" + dates +
                ", times=" + times +
                '}';
    }
}
