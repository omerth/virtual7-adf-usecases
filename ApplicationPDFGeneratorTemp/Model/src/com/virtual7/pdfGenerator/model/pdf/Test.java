package com.virtual7.pdfGenerator.model.pdf;

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;

import java.io.File;
import java.io.OutputStream;

import java.io.StringReader;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import org.xml.sax.SAXException;

public class Test {
    /**
     * Main method.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("FOP ExampleXML2PDF\n");
            System.out.println("Preparing...");

            // Setup directories
            File baseDir = new File("C:/Users/bolat/Desktop/tmp");
            File outDir = new File(baseDir, "out");
            outDir.mkdirs();

            // Setup input and output files
            //            File xmlfile = new File(baseDir, "projectteam.xml");
            File xsltfile = new File(baseDir, "employeesData2.xsl");
            File pdffile = new File(outDir, "test.pdf");

            //            System.out.println("Input: XML (" + xmlfile + ")");
            System.out.println("Stylesheet: " + xsltfile);
            System.out.println("Output: PDF (" + pdffile + ")");
            System.out.println();
            System.out.println("Transforming...");

            // configure fopFactory as desired
            FopFactory fopFactory = FopFactory.newInstance();

            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            // configure foUserAgent as desired

            // Setup output
            OutputStream out = new java.io.FileOutputStream(pdffile);
            out = new java.io.BufferedOutputStream(out);

            try {
                // Construct fop with desired output format
                Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

                // Setup XSLT
                TransformerFactory factory = new TransformerFactoryImpl();
                javax.xml.transform.Transformer transformer = factory.newTransformer(new StreamSource(xsltfile));

                // Set the value of a <param> in the stylesheet
                transformer.setParameter("versionParam", "2.0");

                // Setup input for XSLT transformation
                //                Source src = new StreamSource(xmlfile);
                String s =
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<ROOT>" +
                    "<EMPLOYEE_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Julia</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Nayer</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Irene</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Mikkilineni</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>James</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Landry</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Steven</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Markle</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Laura</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Bissot</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Mozhe</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Atkinson</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>James</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Marlow</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>TJ</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Olson</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Jason</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Mallin</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Michael</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Rogers</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Ki</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Gee</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Hazel</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Philtanker</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Renske</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Ladwig</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Stephen</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Stiles</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>John</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Seo</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Joshua</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Patel</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Trenna</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Rajs</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Curtis</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Davies</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Randall</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Matos</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Peter</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Vargas</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>John</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Russell</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Karen</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Partners</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Alberto</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Errazuriz</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Gerald</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Cambrault</EMPLOYEE_LAST_NAME>" +
                    "<EMPLOYEE_FIRST_NAME>Eleni</EMPLOYEE_FIRST_NAME>" +
                    "<EMPLOYEE_LAST_NAME>Zlotkey</EMPLOYEE_LAST_NAME>" +
                    "</EMPLOYEE_NAME>" +
                    "</ROOT>";

                Source src = new StreamSource(new StringReader(s));
                // Resulting SAX events (the generated FO) must be piped through to FOP
                Result res = new SAXResult(fop.getDefaultHandler());

                // Start XSLT transformation and FOP processing
                transformer.transform(src, res);
            } finally {
                out.close();
            }

            System.out.println("Success!");
        } catch (SAXException e) {
            e.printStackTrace(System.err);
            System.exit(-1);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(-1);
        }
    }
}
