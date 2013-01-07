package com.virtual7.dvtpoc.pdf;

/**
 * Exception for PDF generation.
 */
public class PDFException extends Exception {

    @SuppressWarnings("compatibility:-1379778260750729171")
    private static final long serialVersionUID = 2188730696329289641L;

    public PDFException() {
        super();
    }

    public PDFException(String message) {
        super(message);
    }

    public PDFException(String message, Throwable cause) {
        super(message, cause);
    }

    public PDFException(Throwable cause) {
        super(cause);
    }
}

