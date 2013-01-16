package com.eurofighter.fileupload.avscanner.savapi;


import com.eurofighter.fileupload.avscanner.AAVScanner;
import com.eurofighter.fileupload.avscanner.AVAlertException;
import com.eurofighter.fileupload.avscanner.AVConnectionException;
import com.eurofighter.fileupload.avscanner.AVInfectedException;
import com.eurofighter.fileupload.avscanner.ScanResponse;
import com.eurofighter.fileupload.avscanner.utils.RequestUtils;

import java.io.InputStream;

import java.util.EnumSet;


/**
 * Concrete implementation of an AVScanner for the AVIRA SAVAPI antivirus server.
 */
public class SavapiAVScanner extends AAVScanner {

    /**
     * Initialization parameter for the <code>SavapiAVScanner</code> that configures the levels of AV warnings
     * which are considered as infected. In other words said, when the AV returns one of this level of information
     * regarding the file, that file will be considered as infected and will be treated accordingly (file will be copyed
     * to the INFECTED_FILES_DIR, the user will be notifyed and the uprocessing of the file will be stopped). When the
     * parameter is not defined or is defined with emtpy value all levels are considered as INFECTED.
     * Possible values for this parameter is a String containing a comma separated list with the possible values:
     *
     * 'infected' - the AV sends this level when the file is infected with a virus
     * 'encrypted' - the AV sends this level when the file is encrypted and scanning can not be done (for example a zip protected with password)
     * 'warning' - the AV sends this level when the file can be read but can not be changed (for example an encrypted PDF)
     * 'scanerror' - the AV sends this level when an error occured when scanning the file
     *
     *  <context-param>
     *      <param-name>com.eurofighter.fileupload.processor.AVUploadFileProcessor.INFECTED_FILES_LEVELS</param-name>
     *      <param-value>infected,encrypted,warning,scanerror</param-value>
     *  </context-param>
     */
    private static final String INFECTED_FILES_LEVELS_PARAM_NAME =
        "com.eurofighter.fileupload.processor.AVUploadFileProcessor.INFECTED_FILES_LEVELS";

    /**
     * Enumeration containing the list of all possible Infected File Levels.
     */
    private static enum InfectedFileLevel {
        infected,
        encrypted,
        warning,
        scanerror;
    }

    /**
     * This will keep the infected file levels configured in the  intialization parameter from web.xml
     */
    private EnumSet<InfectedFileLevel> infectedFileLevels;

    /**
     * Default constructor.
     * Is mandatory to have it because the AVScanner classes are instantiated trough reflection using the default constructor.
     */
    public SavapiAVScanner() {
        super();
    }

    /**
     * Override the init method of the parent class to read additional iitialization parameter information.
     *
     * @param context the current ServletContext or PortletContext.
     * @see com.eurofighter.fileupload.avscanner.AAVScanner
     */
    @Override
    public void init(Object context) {
        // Call super intialization functionality.
        super.init(context);

        // Initialize the infected file levels.
        this.infectedFileLevels = EnumSet.noneOf(InfectedFileLevel.class);
        String avLevelsParam = RequestUtils.getInitParameter(context, INFECTED_FILES_LEVELS_PARAM_NAME);
        LOG.info("Read initialization parameter: " + INFECTED_FILES_LEVELS_PARAM_NAME + "=" + avLevelsParam);
        if (avLevelsParam != null) {
            String[] levels = avLevelsParam.split(",");
            if (levels != null && levels.length > 0) {
                for (String level : levels) {
                    if (level != null) {
                        String levelTrimmed = level.trim();
                        // Iterate trough the enum and see if the value is in the enum.
                        for (InfectedFileLevel levelEnumElem : SavapiAVScanner.InfectedFileLevel.values()) {
                            if (levelTrimmed.equalsIgnoreCase(levelEnumElem.name())) {
                                this.infectedFileLevels.add(levelEnumElem);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Perform the scan with AVIRA SAVAPI antivirus.
     *
     * @param fileName the file name to be scanned.
     * @param fileStream the input stream to the file.
     * @return a ScanReponse status in case the scanning is ok.
     */
    @Override
    protected ScanResponse performScan(String fileName, InputStream fileStream) throws AVConnectionException,
                                                                                       AVInfectedException,
                                                                                       AVAlertException {
        LOG.fine("Start AVIRA SAVAPI scanning for the uploaded file:" + fileName);

        // Execute scan.
        AVStreamScanner scanner = AVStreamScanner.getInstance(this.serverName, this.serverPort);
        String resp = scanner.getStreamScanReponse(fileStream, fileName, this.useSSLConnection);
        LOG.fine("Recieved Response from AVIRA SAVAPI:" + resp);

        // Interpret the response.
        ScanObject respObj = new ScanObject();
        respObj.updateScanState(resp);
        if (isInfected(respObj)) {
            LOG.warning("AVIRA SAVAPI Antivirus found infected file:" + fileName);
            throw new AVInfectedException("AVIRA SAVAPI Antivirus found infected file:" + fileName);
        }

        // If this is reached it means scanning was performed correctly.
        LOG.fine("END AVIRA SAVAPI scanning for the uploaded file:" + fileName);
        return new ScanResponse("File cleared by AVIRA SAVAPI Antivirus with response status:" + resp);
    }

    /**
     * Method telling if the object for which the response object was generated is infected or not.
     *
     * @param respObj the response object containig response from the Antivirus Scan Process.
     * @return true if the ScanObject respObj is infected, encrypted, error or warning status and there is a similar
     * AVLevel in the avLevels list for that status.
     */
    private boolean isInfected(ScanObject respObj) {
        return (respObj.isInfected() &&
                (this.infectedFileLevels.isEmpty() || this.infectedFileLevels.contains(SavapiAVScanner.InfectedFileLevel.infected))) ||
            (respObj.isEncrypted() &&
             (this.infectedFileLevels.isEmpty() || this.infectedFileLevels.contains(SavapiAVScanner.InfectedFileLevel.encrypted))) ||
            (respObj.isError() &&
             (this.infectedFileLevels.isEmpty() || this.infectedFileLevels.contains(SavapiAVScanner.InfectedFileLevel.scanerror))) ||
            (respObj.isWarning() &&
             (this.infectedFileLevels.isEmpty() || this.infectedFileLevels.contains(SavapiAVScanner.InfectedFileLevel.warning)));
    }

}
