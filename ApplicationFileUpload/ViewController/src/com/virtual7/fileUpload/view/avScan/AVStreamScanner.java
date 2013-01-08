package com.virtual7.fileUpload.view.avScan;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Date;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import oracle.adf.share.logging.ADFLogger;

/**
 * Useful class for getting the stream of a file and sending it to the SAVAPI scan engine.
 *
 */
public class AVStreamScanner {

    /**
     * The logger for this class.
     */
    private static ADFLogger log = ADFLogger.createADFLogger(AVStreamScanner.class);

    private String scanningServerHost = null;

    private int scanningServerPort;

    private static AVStreamScanner scanner = null;

    private AVStreamScanner(String host, Integer port) {
        this.scanningServerHost = host;
        this.scanningServerPort = port;
    }

    /**
     * Returns an instance of this class if <code>port</code> and <code>host</code> check out.
     *
     * @param host
     *            the host
     * @param port
     *            the port
     * @return a {@link StreamScanner} or null
     */
    public static AVStreamScanner getInstance(String host, String port) {
        if (scanner == null) {
            scanner = getStreamScanner(host, port);
        }
        return scanner;
    }

    /**
     * Returns an instance of this class if <code>port</code> and <code>host</code> check out. The given parameters need
     * to be valid.
     *
     * @param host
     *            the host
     * @param port
     *            the port
     * @return a {@link AVStreamScanner} or null
     */
    @SuppressWarnings("static-access")
    private static AVStreamScanner getStreamScanner(String host, String port) {
        // check if the host is valid
        host = (AVUtils.isValidString(host)) ? host : null;

        // check if the port is valid
        Integer portInteger = AVUtils.parseInteger(port);

        // if all checks out
        if (portInteger != null && host != null) {
            return new AVStreamScanner(host, portInteger);
        } else {
            log.severe("Could not initialize the stream scanner! Port: " + port + ", Host: " + host);
        }
        return null;
    }

    /**
     * Sends the given <code>stream</code> to the scan engine returning a scan response.
     *
     * @param stream
     *            the {@link InputStream}
     * @param fileName
     *            the name of the file
     * @return the scan response as returned by the scanning engine
     * @throws AVServiceException
     */
    public String getStreamScanReponse(InputStream stream, String fileName,
                                       boolean isSSLOn) throws AVServiceException {
        String scanResponse = "";

        long startTime = new Date().getTime();
        scanResponse = sendStreamForScanning(stream, fileName, "", "", isSSLOn);
        long endTime = new Date().getTime();

        log.info("Sending stream to scanning server took: " + (endTime - startTime) + " milliseconds");

        return scanResponse;
    }

    /**
     * Sends the <code>stream</code> to the scanning engine and gets back the scan status.
     *
     * @param stream
     *            the {@link InputStream}
     * @param fileName
     *            the name of the file
     * @return the scan response as returned by the scanning engine
     * @throws AVServiceException
     *             in case of errors
     */
    @SuppressWarnings("static-access")
    private String sendStreamForScanning(InputStream stream, String fileName, String dDocName, String dId,
                                         boolean isSSLOn) throws AVServiceException {
        String response = "";
        Socket socket = null;
        OutputStream outputStream = null;
        BufferedReader in = null;
        ByteArrayOutputStream bos = null;
        DataOutputStream dataOutputStream = null;
        try {
            // create the socket object differentiating between a SSL and normal socket connection
            if (isSSLOn) {
                SSLSocketFactory sslSocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
                socket = (SSLSocket)sslSocketFactory.createSocket(this.scanningServerHost, this.scanningServerPort);
            } else {
                socket = new Socket(this.scanningServerHost, this.scanningServerPort);
            }

            outputStream = socket.getOutputStream();

            int numRead = 0;
            byte b[] = new byte[1024];
            bos = new ByteArrayOutputStream();
            // save stream to array
            while ((numRead = stream.read(b)) > 0) {
                bos.write(b, 0, numRead);
            }
            bos.flush();
            byte[] buffer = bos.toByteArray();

            dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF(dDocName);
            dataOutputStream.writeUTF(dId);
            // write the file name
            dataOutputStream.writeUTF(fileName);
            // write the stream
            dataOutputStream.writeLong(buffer.length);
            dataOutputStream.write(buffer, 0, buffer.length);
            dataOutputStream.flush();

            // wait for the scanning to return the status
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line = "";
            while ((line = in.readLine()) != null) {
                response += line + "\n";
            }

        } catch (UnknownHostException e) {
            log.severe("Error in sendStreamForScanning()", e);
            throw new AVServiceException(e);
        } catch (IOException e) {
            log.severe("Error in sendStreamForScanning()", e);
            throw new AVServiceException(e);
        } finally {
            if (outputStream != null && dataOutputStream != null) {
                try {
                    outputStream.close();
                    dataOutputStream.close();
                } catch (IOException e) {
                    log.severe("Error in sendStreamForScanning() - closing output stream", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.severe("Error in sendStreamForScanning() - closing input stream", e);
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    log.severe("Error in sendStreamForScanning() - closing the socket connection", e);
                }
            }
            if (stream != null && bos != null) {
                try {
                    stream.close();
                    bos.close();
                } catch (IOException e) {
                    log.severe("Error in sendStreamForScanning() - closing the stream", e);
                }
            }
        }
        return response;
    }
}
