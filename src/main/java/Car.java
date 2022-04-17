import java.util.ArrayList;
import java.util.UUID;

public class Car {
    private int id;
    private UUID uuid;
    private String model;
    private ArrayList<Airbag> airbags;
    private int year;
    private String color;
    private boolean invoice;

    public Car(UUID uuid, String model, ArrayList<Airbag> airbags, Integer year, String color) {
        this.uuid = uuid;
        this.model = model;
        this.airbags = airbags;
        this.year = year;
        this.color = color;
        this.invoice = false;
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
                ", invoice=" + invoice +
                '}';
    }

    public ArrayList<Airbag> getAirbags() { return airbags; }

    public void setInvoice(boolean invoice) { this.invoice = invoice; }

    public void setId(int id) {
        this.id = id;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }
}
