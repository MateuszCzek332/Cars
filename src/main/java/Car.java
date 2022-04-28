import java.util.ArrayList;
import java.util.UUID;

public class Car {
    private int id;
    private UUID uuid;
    private String model;
    private ArrayList<Airbag> airbags;
    private String date;
    private String img;
    private int cena;
    private int vat;
    private int year;
    private String color;
    private boolean invoice;

    public Car(UUID uuid, String model, ArrayList<Airbag> airbags, String date, String img, int cena, int vat, int year, String color) {
        this.uuid = uuid;
        this.model = model;
        this.airbags = airbags;
        this.date = date;
        this.img = img;
        this.cena = cena;
        this.vat = vat;
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
                ", date=" + date +
                ", img='" + img + '\'' +
                ", cena=" + cena +
                ", vat=" + vat +
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

    public String getImg() {return img;}

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

    public int getCena() {return cena;}

    public int getVat() {return vat;}

    public void setDate(String date) {this.date = date;}

    public void setImg(String img) {this.img = img;}

    public void setCena(int cena) {this.cena = cena;}

    public void setVat(int vat) {this.vat = vat;}
}
