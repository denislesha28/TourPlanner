package BusinessLayer.Exporting;

import BusinessLayer.Exceptions.JsonExporterException;
import BusinessLayer.Exceptions.TourListManagerException;
import BusinessLayer.Exceptions.TourLogManagerException;
import BusinessLayer.TourListManager;
import BusinessLayer.TourLogManager;
import Datatypes.Tour;
import Datatypes.TourLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonExporter {
    TourLogManager tourLogManager;
    TourListManager tourListManager;
    Logger log;
    public JsonExporter() throws JsonExporterException {
        try {
            tourLogManager = new TourLogManager();
            tourListManager = new TourListManager();
            log = LogManager.getLogger(PDFExporter.class);
        } catch (TourLogManagerException | TourListManagerException e) {
            throw new JsonExporterException("Couldn't get Tour Manager Interfaces",e);
        }

    }

    public void exportJson(String tourName) throws JsonExporterException {
        FileWriter file = null;
        try {
            String jsonTourObject = createJsonTourObject(tourName);
            file = new FileWriter(tourName+"_jsonExport.json");
            file.write(jsonTourObject);
            file.flush();
            file.close();
            log.debug("Writing TourJsonObject to file");
        } catch (IOException e) {
            throw new JsonExporterException("Couldn't write json to file",e);
        }


    }

    private String createJsonTourObject(String tourName) throws JsonExporterException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode tourRoot = mapper.createObjectNode();

        Tour exportTour = null;
        List<TourLog> tourLogList = null;
        try {
            exportTour = tourListManager.getTourAttributes(tourName);
            tourLogList = tourLogManager.getAllTourLogs(tourName);
            log.debug("Retrieved All Tour Data for Json Export");
        } catch (TourListManagerException e) {
            throw new JsonExporterException("could not get tourAttributes in Json Exporter",e);
        }catch (TourLogManagerException e){
            throw new JsonExporterException("could not get tourLogs in Json Exporter",e);
        }

        tourRoot.put("Name",exportTour.getTourName());
        tourRoot.put("TourDistance",exportTour.getTourDistance());
        tourRoot.put("RouteInformation",exportTour.getRouteInformation());
        tourRoot.put("TourDescription",exportTour.getTourDescription());

        ObjectNode tourRouteChild = mapper.createObjectNode();
        tourRouteChild.put("From",exportTour.getTourFrom());
        tourRouteChild.put("To",exportTour.getTourTo());

        tourRoot.set("TourRoute",tourRouteChild);
        log.debug("Added All Tour Attributes for Json Export");

        ObjectNode tourLogsChild = mapper.createObjectNode();
        for (TourLog tourLog: tourLogList){
            ObjectNode tourLogChild = mapper.createObjectNode();
            tourLogChild.put("Author",tourLog.getAuthor());
            tourLogChild.put("LogReport",tourLog.getLogReport());
            tourLogChild.put("TraveledDistance",tourLog.getTraveledDistance());
            tourLogChild.put("Duration",tourLog.getDuration());
            tourLogChild.put("Date",tourLog.getDate().substring(0,10));
            tourLogChild.put("Rating",tourLog.getRating());
            tourLogChild.put("AverageSpeed",tourLog.getAvgSpeed());
            tourLogChild.put("SpecialRemarks",tourLog.getRemarks());
            tourLogChild.put("JouleExpended",tourLog.getJoule());
            tourLogChild.put("Weather",tourLog.getWeather());
            tourLogsChild.set("TourLog - "+tourLog.getTimestamp(),tourLogChild);
        }

        tourRoot.set("TourLogs",tourLogsChild);
        log.debug("Added All TourLog Attributes for Json Export");

        String jsonString = null;
        try {
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tourRoot);
        } catch (JsonProcessingException e) {
            throw new JsonExporterException("json Exporting has failed for selected Tour",e);
        }

        return jsonString;
    }
}
