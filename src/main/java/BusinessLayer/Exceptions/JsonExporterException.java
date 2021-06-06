package BusinessLayer.Exceptions;

public class JsonExporterException extends Exception{
    public JsonExporterException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
