import java.util.ArrayList;

public class Invoice {
    public String time;
    public String title;
    public String seller;
    public String buyer;
    ArrayList<Car> list;

    public Invoice(String seller, String buyer, ArrayList<Car> list) {
        this.time = String.valueOf(System.currentTimeMillis());
        this.seller = seller;
        this.buyer = buyer;
        this.list = list;
        this.generateTitle();
    }

    public void generateTitle(){
        this.title = "Faktura vat nr. " + this.time;
    }

    public int countOne(int i){
        int cena = list.get(i).getCena();
        int vat = list.get(i).getVat();
        return cena * (100 + vat)/100;
    }

    public float countAll(){
        float suma = 0;
        for(int i =0; i<list.size(); i++)
            suma += this.countOne(i);
        return suma;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", seller='" + seller + '\'' +
                ", buyer='" + buyer + '\'' +
                ", list=" + list +
                '}';
    }
}

