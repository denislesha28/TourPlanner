package BusinessLayer;
import DataAccessLayer.Model;
import DataAccessLayer.Tour;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;


import javax.imageio.ImageIO;
import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class PDFExporter
{
    Model model;
    MapApiHttpHandler httpHandler;

    public PDFExporter() throws SQLException, IOException {
        model=Model.getModelInstance();
        httpHandler = new MapApiHttpHandler();
    }


    public void exportTourPdf(String tourName) throws SQLException, URISyntaxException, ExecutionException, InterruptedException {
        Tour tourDetails=model.getTourDetails(0,tourName);
        httpHandler.sendMapApiRequestExportImage(tourDetails.getTourFrom(),tourDetails.getTourTo(),this,tourName);
    }


    public void generateTourReport(String tourName,BufferedImage tourImage) throws DocumentException, IOException, SQLException, DocumentException {

        Document document = new Document();
        Tour tourDetails=model.getTourDetails(0,tourName);
        PdfWriter.getInstance(document, new FileOutputStream("TourReport_"+tourName+".pdf"));
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 22, Font.BOLD);
        Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
        Font subChapterFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);

        Chunk tourLogs = new Chunk(tourName+" Route", chapterFont);
        Chunk tourAttributes = new Chunk(tourName+" Tour Attributes", chapterFont);
        document.add(new Chapter(new Paragraph(tourAttributes),1));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Tour Description: "+tourDetails.getTourDescription(),paragraphFont));
        document.add(new Paragraph("Tour Distance: "+tourDetails.getTourDistance(),paragraphFont));
        document.add(new Paragraph("Route Information: "+tourDetails.getRouteInformation(),paragraphFont));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Tour Route",subChapterFont));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Tour Route From: "+tourDetails.getTourFrom(),paragraphFont));
        document.add(new Paragraph("Tour Route to: "+tourDetails.getTourTo(),paragraphFont));
        document.add(new Paragraph(" "));

        BufferedImage bufferedTourImage = tourImage;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedTourImage, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        document.add(new Jpeg(bytes));
        document.close();

    }

    public void exportToursPdf() throws  IOException, DocumentException {

        Document document = new Document();
        List<Tour> tours=model.getToursDetails();
        PdfWriter.getInstance(document, new FileOutputStream("TourReport_AllTours.pdf"));
        document.open();
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 22, Font.BOLD);
        Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
        Font subChapterFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        int chapterCounter=1;

        for (Tour tour : tours){

            Chunk tourLogs = new Chunk(tour.getTourName()+" Route", chapterFont);
            Chunk tourAttributes = new Chunk(tour.getTourName()+" Tour", chapterFont);

            document.add(new Chapter(new Paragraph(tourAttributes),chapterCounter));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Tour Attributes",subChapterFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Tour Description: "+tour.getTourDescription(),paragraphFont));
            document.add(new Paragraph("Tour Distance: "+tour.getTourDistance(),paragraphFont));
            document.add(new Paragraph("Route Information: "+tour.getRouteInformation(),paragraphFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Tour Route",subChapterFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Tour Route From: "+tour.getTourFrom(),paragraphFont));
            document.add(new Paragraph("Tour Route to: "+tour.getTourTo(),paragraphFont));
            document.add(new Paragraph(" "));

            chapterCounter++;

        }

        document.close();

    }


}
