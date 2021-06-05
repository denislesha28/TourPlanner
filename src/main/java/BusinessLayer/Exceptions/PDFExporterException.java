package BusinessLayer.Exceptions;

public class PDFExporterException extends Exception{
    public PDFExporterException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
