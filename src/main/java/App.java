import com.fasterxml.uuid.Generators;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.*;
import spark.Request;
import spark.Response;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import com.itextpdf.text.pdf.PdfWriter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

import static spark.Spark.*;
import static spark.Spark.staticFiles;

class App {

    static ArrayList<Car> cars = new ArrayList<>();
    public static void main(String[] args) {
        staticFiles.location("/public");
        //index
        post("/add", App::addFunction);
        //cars
        post("/json", App::daneFunction);
        post("/delete", App::delFunction);
        post("/update", App::updateFunction);
        //admin
        post("/generate", App::generateFunction); // generowanie bazy aut
        post("/invoice", App::invoiceFunction); // generowanie faktury
        get("/invoices", App::downloadFunction); // pobranie faktury
        //Search
        post("/invoiceAll", App::invoiceAllCarsFunction); // generowanie faktury za wszyskie auta
        post("/invoiceByYear", App::invoiceByYearFunction); // generowanie faktury po roku
        post("/invoiceByPrize", App::invoiceByPrizeFunction); // generowanie faktury po cenie
    }

    static String addFunction(Request req, Response res) {

        Gson gson = new Gson();
        Car newcar = gson.fromJson(req.body(), Car.class);
        newcar.setUuid(Generators.randomBasedGenerator().generate());
        if (cars.isEmpty())
            newcar.setId(1);
        else
            newcar.setId(cars.get(cars.size() - 1).getId() + 1);

        cars.add(newcar);
        return gson.toJson(newcar);
    }

    static String daneFunction(Request req, Response res) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Car>>() {}.getType();
        return gson.toJson(cars, listType);
    }

    static String delFunction(Request req, Response res) {
        for (Car el : cars) {
            if (Objects.equals(req.body().substring(1, 37), el.getUuid().toString())) {
                cars.remove(el);
                break;
            }
        }
        return req.body();
    }

    static String updateFunction(Request req, Response res) {
        Gson gson = new Gson();
        Car updateCar = gson.fromJson(req.body(), Car.class);
        System.out.println(updateCar);
        for (Car el : cars) {
            if (Objects.equals(el.getUuid(), updateCar.getUuid())) {
                System.out.println(el);
                el.setModel(updateCar.getModel());
                el.setYear(updateCar.getYear());
            }
        }
        return req.body();
    }

    static String generateFunction(Request req, Response res) {
        Gson gson = new Gson();
        Helpers h = new Helpers();

        Car newcar = h.getRandomCar();
        newcar.setUuid(Generators.randomBasedGenerator().generate());
        if (cars.isEmpty())
            newcar.setId(1);
        else
            newcar.setId(cars.get(cars.size() - 1).getId() + 1);

        cars.add(newcar);
        System.out.println(newcar);

        Type listType = new TypeToken<ArrayList<Car>>() {
        }.getType();
        return gson.toJson(cars, listType);
    }

    static String invoiceFunction(Request req, Response res) {
        for (Car el : cars) {
            if (Objects.equals(req.body().substring(1, 37), el.getUuid().toString())) {
                try {
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/public/katalog/" + el.getUuid() + ".pdf"));
                    document.open();
                    Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

                    Paragraph docUUID = new Paragraph("Faktura dla " + el.getUuid().toString(), font);
                    Paragraph docModel = new Paragraph("Model: " + el.getModel(), font);
                    Paragraph docKolor = new Paragraph("Rok: " + el.getYear(), font);

                    document.add(docUUID);
                    document.add(docModel);
                    document.add(docKolor);

                    for(Airbag airbag: el.getAirbags()){
                        Paragraph docAirbag = new Paragraph("Poduszki: " + airbag.getDescription() + " - " + airbag.isValue(), font);
                        document.add(docAirbag);
                    }

                    Image img = Image.getInstance("src/main/resources/public/img/" + el.getImg() );
                    document.add(img);
                    document.close();
                    el.setInvoice(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return req.body();
    }

    static String invoiceAllCarsFunction(Request req, Response res) {
        Gson gson = new Gson();
        Invoice i = new Invoice("sprzedawca aut", "nabywca", cars );
        System.out.println(i);
        Invoices inv =  new Invoices(i);
        String odp =  inv.allCars();

        return gson.toJson(odp);
    }

    static String invoiceByYearFunction(Request req, Response res) {
        Gson gson = new Gson();

        int year = Integer.parseInt( req.body() );
        ArrayList<Car> newCars = new ArrayList<>();

        for(Car el: cars){
            if(Objects.equals(year, el.getYear()))
                newCars.add(el);
        }

        Invoice i = new Invoice("sprzedawca aut", "nabywca", newCars );
        System.out.println(i);
        Invoices inv =  new Invoices(i);
        String odp =  inv.carsByYear( year);

        return gson.toJson(odp);
    }

    static String invoiceByPrizeFunction(Request req, Response res) {
        Gson gson = new Gson();

        System.out.println(req.body());

        int l = req.body().indexOf("-");
        int min = Integer.parseInt( req.body().substring(0, l) );
        int max = Integer.parseInt( req.body().substring(l+1));

        ArrayList<Car> newCars = new ArrayList<>();

        for(Car el: cars)
            if (el.getCena() > min && el.getCena() < max)
                newCars.add(el);

        Invoice i = new Invoice("sprzedawca aut", "nabywca", newCars );
        System.out.println(i);
        Invoices inv =  new Invoices(i);
        String odp =  inv.carsByPrize(min, max);

        return gson.toJson(odp);
    }

    static String downloadFunction(Request req, Response res) {

        String uuid =  req.queryParams("uuid");
        res.type("application/octet-stream"); //
        res.header("Content-Disposition", "attachment; filename=Faktura-" +uuid+".pdf"); // nagłówek

        try {
            OutputStream outputStream = res.raw().getOutputStream();
            outputStream.write(Files.readAllBytes(Path.of("src/main/resources/public/katalog/" + uuid+ ".pdf"))); // response pliku do przeglądarki
        } catch (IOException e) {
            e.printStackTrace();
        }

        return req.body();
    }
}

