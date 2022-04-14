import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

public class Car {
    private int id;
    private UUID uuid;
    private String model;
    private ArrayList<Airbag> airbags;
    private int year;
    private String color;

    public Car(String model, ArrayList<Airbag> airbags, int year, String color) {
        this.model = model;
        this.airbags = airbags;
        this.year = year;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", model='" + model + '\'' +
                ", airbags=" + airbags +
                ", year=" + year +
                ", color='" + color + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }
}
