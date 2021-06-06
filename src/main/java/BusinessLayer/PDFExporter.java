package BusinessLayer;
import BusinessLayer.Exceptions.MapApiHandlerException;
import BusinessLayer.Exceptions.PDFExporterException;
import BusinessLayer.Exceptions.TourListManagerException;
import BusinessLayer.Exceptions.TourLogManagerException;
import DataAccessLayer.Model;
import Datatypes.Tour;
import Datatypes.TourLog;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;


import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PDFExporter
{
    TourListManager tourListManager;
    TourLogManager tourLogManager;
    MapApiHttpHandler httpHandler;

    public PDFExporter() throws PDFExporterException {
        try {
            tourListManager = new TourListManager();
            tourLogManager = new TourLogManager();
            httpHandler = new MapApiHttpHandler();
        } catch (TourListManagerException | TourLogManagerException | MapApiHandlerException e) {
            throw new PDFExporterException("could not get necessary Tour Managers for Exporting",e);
        }

    }


    public void exportTourPdf(String tourName) throws PDFExporterException {
        Tour tourDetails= null;
        try {
            tourDetails = tourListManager.getTourAttributes(tourName);
            httpHandler.sendMapApiRequestExportImage(tourDetails.getTourFrom(),tourDetails.getTourTo(),this,tourName);
        } catch (TourListManagerException e) {
            throw new PDFExporterException("could not get TourDetails",e);
        } catch (MapApiHandlerException e){
            throw new PDFExporterException("could not construct HttpRequest",e);
        }

    }


    public void generateTourReport(String tourName,BufferedImage tourImage) throws PDFExporterException {

        Document document = new Document();
        Tour tourDetails= null;
        try {
            tourDetails = tourListManager.getTourAttributes(tourName);
            List<TourLog> tourLogList = tourLogManager.getAllTourLogs(tourName);
            PdfWriter.getInstance(document, new FileOutputStream("TourReport_"+tourName+".pdf"));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 22, Font.BOLD);
            Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
            Font subChapterFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
            Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);

            Chunk tourLogs = new Chunk("TourLogs for "+tourName, chapterFont);
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

            int logCounter=0;
            document.add(new Chapter(new Paragraph(tourLogs),2));
            document.add(new Paragraph(" "));
            for (TourLog tourLog : tourLogList) {
                logCounter++;
                document.add(new Paragraph("Tour Log "+logCounter, subChapterFont));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("TourLog Author: " + tourLog.getAuthor(), paragraphFont));
                document.add(new Paragraph("TourLog Report: " + tourLog.getLogReport(), paragraphFont));
                document.add(new Paragraph("TourLog Traveled Distance: " + tourLog.getTraveledDistance(), paragraphFont));
                document.add(new Paragraph("TourLog Duration: " + tourLog.getDuration(), paragraphFont));
                document.add(new Paragraph("TourLog Date: " + tourLog.getTimestamp().substring(0,10), paragraphFont));
                document.add(new Paragraph("TourLog Rating: " + tourLog.getRating(), paragraphFont));
                document.add(new Paragraph("TourLog Average Speed: " + tourLog.getAvgSpeed(), paragraphFont));
                document.add(new Paragraph("TourLog Special Remarks: " + tourLog.getRemarks(), paragraphFont));
                document.add(new Paragraph("TourLog Joule expended: " + tourLog.getJoule(), paragraphFont));
                document.add(new Paragraph("TourLog Weather: " + tourLog.getWeather(), paragraphFont));
                document.add(new Paragraph(" "));
            }

        } catch (TourListManagerException | DocumentException | TourLogManagerException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        document.close();

    }

    public void exportToursPdf() throws PDFExporterException {

        Document document = new Document();
        List<Tour> tours = tourListManager.getAllToursAttributes();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("TourReport_AllTours.pdf"));
            document.open();
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 22, Font.BOLD);
            Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
            Font subChapterFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
            Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            int chapterCounter=1;

            for (Tour tour : tours){

                Chunk tourLogs = new Chunk("TourLogs for "+tour.getTourName(), chapterFont);
                Chunk tourAttributes = new Chunk(tour.getTourName()+" Tour Attributes", chapterFont);


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

                HashMap<String,String> averages = calculateStatsTourLogs(tour.getTourName());
                document.add(new Paragraph("Tour Logs Summary",subChapterFont));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Tour Logs Average Speed: "+averages.get("avgSpeed"),paragraphFont));
                document.add(new Paragraph("Tour Logs Average Distance: "+averages.get("avgDistance"),paragraphFont));
                document.add(new Paragraph("Tour Logs Average Duration: "+averages.get("avgDuration"),paragraphFont));
                document.add(new Paragraph("Tour Logs Average Joule: "+averages.get("avgJoule"),paragraphFont));
                document.add(new Paragraph("Tour Logs Average Rating: "+averages.get("avgRating"),paragraphFont));

                chapterCounter++;

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.close();

    }

    private HashMap<String,String> calculateStatsTourLogs(String tourName) throws PDFExporterException {
        List<TourLog> tourLogList = null;
        try {
            tourLogList = tourLogManager.getAllTourLogs(tourName);
            Double tourLogsTotalSpeed=0.0;
            Double tourLogsTotalDistance=0.0;
            Double tourLogsTotalDuration=0.0;
            Double tourLogsTotalJoule=0.0;
            int tourLogsTotalRatings=0;
            int tourLogsCount = 0;

            for (TourLog tourLog: tourLogList){
                tourLogsTotalSpeed += tourLog.getAvgSpeed();
                tourLogsTotalDistance += tourLog.getTraveledDistance();
                tourLogsTotalDuration += tourLog.getDuration();
                tourLogsTotalJoule += tourLog.getJoule();
                tourLogsTotalRatings += tourLog.getRating();
                tourLogsCount ++;
            }

            HashMap<String,String> averages = new HashMap<>();
            averages.put("avgSpeed",""+tourLogsTotalSpeed / tourLogsCount);
            averages.put("avgDistance",""+tourLogsTotalDistance / tourLogsCount);
            averages.put("avgDuration",""+tourLogsTotalDuration / tourLogsCount);
            averages.put("avgJoule",""+tourLogsTotalJoule / tourLogsCount);
            averages.put("avgRating",""+ (tourLogsTotalRatings + tourLogsCount - 1) / tourLogsCount);

            return averages;
        } catch (TourLogManagerException e) {
            throw new PDFExporterException("could not get TourLogs data for Exporting",e);
        }
    }


}
