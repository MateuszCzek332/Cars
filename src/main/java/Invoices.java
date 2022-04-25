import com.itextpdf.text.*;
import java.io.FileOutputStream;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class Invoices {
    Invoice invoice;

    public Invoices(Invoice invoice) {
        this.invoice = invoice;
    }

    public String allCars() {
        String fileName = "all_cars_" + invoice.time;
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/public/katalog/" + fileName + ".pdf"));
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

            Paragraph docTitle = new Paragraph(invoice.title, font);
            Paragraph docBuyer = new Paragraph("Nabywca: " + invoice.buyer, font);
            Paragraph docSeller = new Paragraph("Sprzedawca: " + invoice.seller, font);
            Paragraph docDes = new Paragraph("Faktura za wszystkie auta", font);
            Paragraph docCount = new Paragraph("Do zapłaty: " + invoice.countAll(), font);

            PdfPTable table = new PdfPTable(5);

            for (int i = 0; i < invoice.list.toArray().length; i++) {
                PdfPCell lp = new PdfPCell(new Phrase(String.valueOf(i + 1), font));
                PdfPCell rok = new PdfPCell(new Phrase(String.valueOf(invoice.list.get(i).getYear()), font));
                PdfPCell cena = new PdfPCell(new Phrase(String.valueOf(invoice.list.get(i).getCena()), font));
                PdfPCell vat = new PdfPCell(new Phrase(String.valueOf(invoice.list.get(i).getVat()), font));
                PdfPCell wartosc = new PdfPCell(new Phrase(String.valueOf(invoice.countOne(i)), font));
                table.addCell(lp);
                table.addCell(rok);
                table.addCell(cena);
                table.addCell(vat);
                table.addCell(wartosc);
            }

            document.add(docTitle);
            document.add(docBuyer);
            document.add(docSeller);
            document.add(docDes);
            document.add(table);
            document.add(docCount);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public String carsByYear( int year) {
        String fileName = "cars_by_year_" + invoice.time;
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/public/katalog/" + fileName + ".pdf"));
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

            Paragraph docTitle = new Paragraph(invoice.title, font);
            Paragraph docBuyer = new Paragraph("Nabywca: " + invoice.buyer, font);
            Paragraph docSeller = new Paragraph("Sprzedawca: " + invoice.seller, font);
            Paragraph docDes = new Paragraph("Faktura za auta z roku " + year , font);
            Paragraph docCount = new Paragraph("Do zapłaty: " + invoice.countAll(), font);

            PdfPTable table = new PdfPTable(5);

            for (int i = 0; i < invoice.list.toArray().length; i++) {
                PdfPCell lp = new PdfPCell(new Phrase(String.valueOf(i + 1), font));
                PdfPCell rok = new PdfPCell(new Phrase(String.valueOf(invoice.list.get(i).getYear()), font));
                PdfPCell cena = new PdfPCell(new Phrase(String.valueOf(invoice.list.get(i).getCena()), font));
                PdfPCell vat = new PdfPCell(new Phrase(String.valueOf(invoice.list.get(i).getVat()), font));
                PdfPCell wartosc = new PdfPCell(new Phrase(String.valueOf(invoice.countOne(i)), font));
                table.addCell(lp);
                table.addCell(rok);
                table.addCell(cena);
                table.addCell(vat);
                table.addCell(wartosc);
            }

            document.add(docTitle);
            document.add(docBuyer);
            document.add(docSeller);
            document.add(docDes);
            document.add(table);
            document.add(docCount);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public String carsByPrize( int min, int max) {
        String fileName = "cars_by_prize_" + invoice.time;
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/public/katalog/" + fileName + ".pdf"));
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

            Paragraph docTitle = new Paragraph(invoice.title, font);
            Paragraph docBuyer = new Paragraph("Nabywca: " + invoice.buyer, font);
            Paragraph docSeller = new Paragraph("Sprzedawca: " + invoice.seller, font);
            Paragraph docDes = new Paragraph("Faktura za auta o wartości: " + min + "-" + max , font);
            Paragraph docCount = new Paragraph("Do zapłaty: " + invoice.countAll(), font);

            PdfPTable table = new PdfPTable(5);

            for (int i = 0; i < invoice.list.toArray().length; i++) {
                PdfPCell lp = new PdfPCell(new Phrase(String.valueOf(i + 1), font));
                PdfPCell rok = new PdfPCell(new Phrase(String.valueOf(invoice.list.get(i).getYear()), font));
                PdfPCell cena = new PdfPCell(new Phrase(String.valueOf(invoice.list.get(i).getCena()), font));
                PdfPCell vat = new PdfPCell(new Phrase(String.valueOf(invoice.list.get(i).getVat()), font));
                PdfPCell wartosc = new PdfPCell(new Phrase(String.valueOf(invoice.countOne(i)), font));
                table.addCell(lp);
                table.addCell(rok);
                table.addCell(cena);
                table.addCell(vat);
                table.addCell(wartosc);
            }

            document.add(docTitle);
            document.add(docBuyer);
            document.add(docSeller);
            document.add(docDes);
            document.add(table);
            document.add(docCount);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
}

