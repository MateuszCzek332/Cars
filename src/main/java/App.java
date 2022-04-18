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
import java.util.Date;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
;
import static spark.Spark.*;
import static spark.Spark.staticFiles;

class App {

    static ArrayList<Car> cars = new ArrayList<>();
    public static void main(String[] args) {
        staticFiles.location("/public");
        //index
        post("/add", (req, res) -> addFunction(req, res));
        //cars
        post("/json", (req, res) -> daneFunction(req, res));
        post("/delete", (req, res) -> delFunction(req, res));
        post("/update", (req, res) -> updateFunction(req, res));
        //admin
        post("/generate", (req, res) -> generateFunction(req, res)); // generowanie bazy aut
        post("/invoice", (req, res) -> invoiceFunction(req, res)); // generowanie faktury
        get("/invoices", (req, res) -> downloadFunction(req, res)); // pobranie faktury
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
        Type listType = new TypeToken<ArrayList<Car>>() {
        }.getType();
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
        Random random = new Random();
        String[] models = {"Opel", "Ford", "Honda"};

        String newmodel = models[random.nextInt(models.length)];
        int newyear = 1950 + random.nextInt(73);
        String randomColor = " rgb(" + random.nextInt(256) + ", " + random.nextInt(256) + ", " + random.nextInt(256) + ")";
        ArrayList<Airbag> newairbags = new ArrayList<>();
        newairbags.add(new Airbag("kierowca", random.nextBoolean()));
        newairbags.add(new Airbag("pasazer", random.nextBoolean()));
        newairbags.add(new Airbag("kanapa", random.nextBoolean()));
        newairbags.add(new Airbag("boczne", random.nextBoolean()));


        Car newcar = new Car(null, newmodel, newairbags, newyear, randomColor);
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
                    Random random = new Random();
                    String[] imgs = {"img1.jpg", "img2.jpg", "img3.jpg"};
                    Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

                    Paragraph docUUID = new Paragraph("Faktura dla " + el.getUuid().toString(), font);
                    Paragraph docModel = new Paragraph("Model: " + el.getModel(), font);
                    Paragraph docKolor = new Paragraph("Rok: " + String.valueOf(el.getYear()), font);

                    document.add(docUUID);
                    document.add(docModel);
                    document.add(docKolor);

                    for(Airbag airbag: el.getAirbags()){
                        Paragraph docAirbag = new Paragraph("Poduszki: " + airbag.getDescription() + " - " + airbag.isValue(), font);
                        document.add(docAirbag);
                    }

                    Image img = Image.getInstance("src/main/resources/public/img/" + imgs[random.nextInt(imgs.length)] );
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

