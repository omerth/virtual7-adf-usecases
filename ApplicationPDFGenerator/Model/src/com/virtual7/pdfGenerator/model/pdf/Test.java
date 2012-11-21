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
            File baseDir = new File("D:\\POCs\\ApplicationPDFGeneratorTemp\\Model\\src");
            File outDir = new File(baseDir, "out");
            outDir.mkdirs();

            // Setup input and output files
            //            File xmlfile = new File(baseDir, "projectteam.xml");
            File xsltfile = new File(baseDir, "fbc.xsl");
            File pdffile = new File(outDir, "testFbc.pdf");

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
                    "<fbc>" +
                    "<items_to_change_tr>" +
                    "<item> A/C Systems (Spec which) </item>" +
                    "<item> bbb </item>" +
                    "</items_to_change_tr>" +
                    "<items_to_change_tr>" +
                    "<item> ... </item>" +
                    "</items_to_change_tr>" +
                    "<items_to_change_tr>" +
                    "<item> ASTA </item>" +
                    "</items_to_change_tr>" +
                    "<items_to_change_tr>" +
                    "<item> GSS </item>" +
                    "</items_to_change_tr>" +
                    "<items_to_change_tr>" +
                    "<item> AGRED (Spec. which) </item>" +
                    "</items_to_change_tr>" +
                    "</fbc>";

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
