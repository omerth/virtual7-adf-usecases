package com.virtual7.dvtpoc.pdf;


import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import oracle.adf.share.logging.ADFLogger;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;


/**
 * PDF Generator class using FOP.
 */
public class FOPPDFGenerator {

    /**
     * Logger for the class.
     */
    private static final ADFLogger LOG = ADFLogger.createADFLogger(FOPPDFGenerator.class);

    /**
     * Private constructor to avoid instantiation.
     */
    private FOPPDFGenerator() {
        super();
    }

    /**
     * Generate a PDF using FOP. The method will not close any of the passed streams, so is the responsibility of the caller to handle the stream closing.
     *
     * @param xml the source XML.
     * @param xsl the transformation XSL.
     * @param pdf the output stream where the PDF will be written.
     * @throws PDFException in case an error occours while generating the PDF.
     */
    public static void generatePDF(InputStream xml, InputStream xsl, OutputStream pdf) throws PDFException {
        try {
            // Instantiate the fop factory and user agent.
            FopFactory fopFactory = FopFactory.newInstance();
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

            // Setup input XML for XSLT transformation.
            Source src = new StreamSource(xml);

            // Setup XSLT transformer.
            TransformerFactory factory = new TransformerFactoryImpl();
            Transformer transformer = factory.newTransformer(new StreamSource(xsl));

            // Setup output PDF. Resulting SAX events (the generated FO) must be piped through to FOP.
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, pdf);
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing.
            transformer.transform(src, res);
        } catch (Throwable e) {
            throw new PDFException(e);
        }
    }

    /**
     * Generate the PDF by transforming the xml with the xsl given by the name.
     *
     * @param xml the xml.
     * @param xslFileName the name of the xsl file which has to be in the root package so that it can be found by the class loader.
     * @pdf the outputstream where to write the content.
     * @throws PDFException in case an error occoures.
     */
    public static void generatePDF(String xml, String xslFileName, OutputStream pdf) throws PDFException {
        if (xml != null && xslFileName != null) {
            InputStream xmlStream = null;
            InputStream xslStream = null;
            try {
                // Construct the xml.
                xmlStream = new ByteArrayInputStream(xml.getBytes());

                // Read the xsl.
                xslStream = FOPPDFGenerator.class.getClassLoader().getResourceAsStream(xslFileName);

                // Generate the PDF.
                FOPPDFGenerator.generatePDF(xmlStream, xslStream, pdf);
                pdf.flush();
            } catch (Throwable e) {
                LOG.severe("Error on generating the PDF!", e);
                throw new PDFException(e);
            } finally {
                // Close the streams.
                if (xmlStream != null) {
                    try {
                        xmlStream.close();
                    } catch (Throwable e) {
                        LOG.warning("Error while closing the XML input stream!", e);
                    }
                }

                if (xslStream != null) {
                    try {
                        xslStream.close();
                    } catch (Throwable e) {
                        LOG.warning("Error while closing the XSL input stream!", e);
                    }
                }

            }
        }
    }

    /**
     * Generate the PDF by transforming the xml with the xsl given by the name.
     *
     * @param xml the xml.
     * @param xslFileName the name of the xsl file which has to be in the root package so that it can be found by the class loader.
     * @return an input stream containing the PDF.
     * @throws PDFException in case an error occoures.
     */
    public static InputStream generatePDF(String xml, String xslFileName) throws PDFException {
        ByteArrayInputStream pdf = null;

        if (xml != null && xslFileName != null) {
            InputStream xmlStream = null;
            InputStream xslStream = null;
            ByteArrayOutputStream outPDF = null;
            try {
                // Construct the xml.
                xmlStream = new ByteArrayInputStream(xml.getBytes());

                // Read the xsl.
                xslStream = FOPPDFGenerator.class.getClassLoader().getResourceAsStream(xslFileName);

                // Create the outputstream where the PDF will be written.
                outPDF = new ByteArrayOutputStream();

                // Generate the PDF.
                FOPPDFGenerator.generatePDF(xmlStream, xslStream, outPDF);
                outPDF.flush();

                // Transform the output stream to input stream for return.
                pdf = new ByteArrayInputStream(outPDF.toByteArray());
            } catch (Throwable e) {
                LOG.severe("Error on generating the PDF!", e);
                throw new PDFException(e);
            } finally {
                // Close the streams.
                if (xmlStream != null) {
                    try {
                        xmlStream.close();
                    } catch (Throwable e) {
                        LOG.warning("Error while closing the XML input stream!", e);
                    }
                }

                if (xslStream != null) {
                    try {
                        xslStream.close();
                    } catch (Throwable e) {
                        LOG.warning("Error while closing the XSL input stream!", e);
                    }
                }

                if (outPDF != null) {
                    try {
                        outPDF.close();
                    } catch (Throwable e) {
                        LOG.warning("Error while closing the PDF output stream!", e);
                    }
                }
            }
        }
        return pdf;
    }
}
