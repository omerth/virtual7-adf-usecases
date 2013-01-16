package com.eurofighter.fileupload.av.mock;

import com.eurofighter.fileupload.av.AVConnectionException;
import com.eurofighter.fileupload.av.AVInfectedException;
import com.eurofighter.fileupload.av.AbsAVScanner;
import com.eurofighter.fileupload.av.utils.RequestUtils;
import com.eurofighter.fileupload.processor.AVUploadFileProcessor;


import java.io.IOException;

import java.util.EnumSet;

import java.util.Scanner;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidad.model.UploadedFile;

/**
 * This class simulate an antiviruss. Will read a file and if detect in that file or in it name one of the words given
 * in a configurable list, will mark that file as infected
 */
public class MockScanner extends AbsAVScanner{
    /**
     * Private logger.
     */
    private static final ADFLogger LOG = ADFLogger.createADFLogger(MockScanner.class);
    /**
     * Initialization parameter for the <code>AVUploadFileProcessor</code> that configures the levels of AV warnings
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
        "com.eurofighter.fileupload.av.mock.MockScanner.INFECTED_FILES_LEVELS";
    
    //Each level have a list of words. If a word from from this list is found in the scanned file or in it name, then
    //that file will be considered infected, and it infection level will be given by the level associated with that
    //list. For example, if the wordsForInfectedLevel list contain the word "infected" and the scanned file or it name
    //contain this word too, then the infection level of the file will be level - infected (from InfectedFileLevels 
    //EnumSet)
    private static final String wordsForInfectedLevel[] = new String[]{"infected", "infected2"};
    private static final String wordsForEncryptedLevel[] = new String[]{"encrypted", "encrypted2"};
    private static final String wordsForWarningLevel[] = new String[]{"warning", "warning2"};
    private static final String wordsForScanErrorLevel[] = new String[]{"scanError", "errorScan", "scanErrror2", "scanerror"};


    /**
     * Enumeration containing the list of all possible Infected File Levels.
     */
    
    private static enum InfectedFileLevel {
        infected,
        encrypted,
        warning,
        scanerror;
    }
    
    private EnumSet<InfectedFileLevel> infectedFileLevels;
    
//    public MockScanner(AVUploadFileProcessor fileProcessor) {
//        super(fileProcessor);
//        initAvLevels(fileProcessor.getContext());
//    }
    public void init(Object context) {
        super.init(context);
        initAvLevels(context);
    }

    public void scan(UploadedFile uploadedFile) throws AVConnectionException, AVInfectedException,
                                                       AVEncryptedException, AVWarningException, AVScanErrorException {
        Scanner fileReader = null;
        String fileName = uploadedFile.getFilename();
        testText(fileName, fileName);
        try {
            fileReader = new Scanner(uploadedFile.getInputStream());
        } catch (IOException e) {
            throw new AVConnectionException();
        }
        if(fileReader != null){
            while(fileReader.hasNext()){
                String line = fileReader.nextLine();
                testText(fileName, line);
            }
        }
        
    }
    /**
     * Init the avLevels List depending on the anti-virus levels list from the web.xml.
     *
     * @param context that need to be pressed as reference on init(Object context) method
     */
    private void initAvLevels(Object context) {
        // Initialize the infected file levels with empty list.
        this.infectedFileLevels = EnumSet.noneOf(InfectedFileLevel.class);

        // Read parameter.
        String avLevelsParam = RequestUtils.getInitParameter(context, INFECTED_FILES_LEVELS_PARAM_NAME);
        LOG.info(INFECTED_FILES_LEVELS_PARAM_NAME + "=" + avLevelsParam);
        if (avLevelsParam != null) {
            String[] levels = avLevelsParam.split(",");
            if (levels != null && levels.length > 0) {
                for (String level : levels) {
                    if (level != null) {
                        String levelTrimmed = level.trim();
                        // Iterate trough the enum and see if the value is in the enum.
                        for (InfectedFileLevel levelEnumElem : InfectedFileLevel.values()) {
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
     * @param scannedText
     * @return true if the infectedFileLevels contain level "infected" and the scannedText contain one of the strings from
     * wordsForInfectedLevel array
     */
    private boolean isInfected(String scannedText){
        boolean result = false;
        if(infectedFileLevels.contains(InfectedFileLevel.infected)){  
            for (String word : wordsForInfectedLevel) {
                if(scannedText.contains(word)){
                    return true;
                }
            }
        }
        return result;
    }
    /**
     * @param scannedText
     * @return true if the infectedFileLevels contain level "encrypted" and the scannedText contain one of the strings from
     * wordsForInfectedLevel array
     */
    private boolean isEncrypted(String scannedText){
        boolean result = false;
        if(infectedFileLevels.contains(InfectedFileLevel.encrypted)){
            for (String word : wordsForEncryptedLevel) {
                if(scannedText.toLowerCase().contains(word.toLowerCase())){
                    return true;
                }
            }
        }
        return result;
    }
    /**
     * @param scannedText
     * @return true if the infectedFileLevels contain level "warning" and the scannedText contain one of the strings from
     * wordsForInfectedLevel array
     */
    private boolean isWarning(String scannedText){
        boolean result = false;
        if(infectedFileLevels.contains(InfectedFileLevel.warning)){
            for (String word : wordsForWarningLevel) {
                if(scannedText.toLowerCase().contains(word.toLowerCase())){
                    return true;
                }
            }
        }
        return result;
    }
    /**
     * @param scannedText
     * @return true if the infectedFileLevels contain level "scanError" and the scannedText contain one of the strings from
     * wordsForInfectedLevel array
     */
    private boolean isScanError(String scannedText){
        boolean result = false;
        if(infectedFileLevels.contains(InfectedFileLevel.scanerror)){
            for (String word : wordsForScanErrorLevel) {
                if(scannedText.toLowerCase().contains(word.toLowerCase())){
                    return true;
                }
            }
        }
        return result;
    }
    
    private void testText(String fileName,String scannedText) throws AVInfectedException, AVEncryptedException, AVWarningException,
                                                     AVScanErrorException {
        if(isInfected(scannedText)){
            throw new AVInfectedException();
        }
        if(isEncrypted(scannedText)){
            throw new AVEncryptedException("The file " + fileName + " is infected(encrypted level), so was not processed");
        }
        if(isWarning(scannedText)){
            throw new AVWarningException("The file " + fileName + " is infected(warning level), so was not processed");
        }
        if(isScanError(scannedText)){
            throw new AVScanErrorException("The file " + fileName + " is infected(scanError level), so was not processed");
        }
    }
}
