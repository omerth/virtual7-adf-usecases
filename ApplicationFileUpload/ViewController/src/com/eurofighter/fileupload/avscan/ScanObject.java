package com.eurofighter.fileupload.avscan;

import java.util.Date;

/**
 * Model used in saving the scan status information into the table EAP_CDS_REV_AV_SCAN_INFO and exposing useful methods
 * for handling the scan status.
 *
 */
public class ScanObject {

    public static final Integer AV_SCAN_NOT_INFECTED_DB_STATE = 0;

    public static final Integer AV_SCAN_INFECTED_DB_STATE = 1;

    public static final Integer AV_SCAN_ENCRYPTED_DB_STATE = 2;

    public static final Integer AV_SCAN_ERROR_DB_STATE = 3;

    public static final Integer AV_SCAN_WARNING_DB_STATE = 4;

    private String dId;

    private Date lastScanned;

    private String scanStatusMessage;

    private int scanTimes;

    private boolean isInfected;

    private boolean isEncrypted;

    private boolean isWarning;

    private boolean isError;

    /**
     * Checks if the current scann object is a SAVAPI alert(infected, encrypted, error).
     *
     * @return a boolean value stating if the scan object is or is not a SAVAPI alert
     */
    public boolean isAlert() {
        if (isInfected || isEncrypted || isError) {
            return true;
        }
        return false;
    }

    /**
     * Changes the scan state of the scan object according to the received scan status.
     *
     * @param savapiStatus
     *            the received status
     */
    public void updateScanState(String savapiStatus) {
        if (AVUtils.isValidString(savapiStatus)) {
            String[] scanReport = savapiStatus.split(",");
            // the status contain a report like string
            if (scanReport.length > 4) {
                // infected state
                setInfected(new Boolean(scanReport[0]));
                // encrypted state
                setEncrypted(new Boolean(scanReport[1]));
                // warning state
                setWarning(new Boolean(scanReport[2]));
                // error state
                setError(new Boolean(scanReport[3]));
                // the scan message
                setScanStatusMessage(scanReport[4]);
            }
        }
    }

    /**
     * Changes the scan state of the scan object according to the received DB scan state.
     *
     * @param state
     *            the DB scan state of the revision
     */
    public void updateScanState(int state) {
        if (state == AV_SCAN_INFECTED_DB_STATE) {
            setInfected(true);
        } else if (state == AV_SCAN_ENCRYPTED_DB_STATE) {
            setEncrypted(true);
        } else if (state == AV_SCAN_WARNING_DB_STATE) {
            setWarning(true);
        } else if (state == AV_SCAN_ERROR_DB_STATE) {
            setError(true);
        }
    }

    /**
     * Translates the scan state of the scan object into the corresponding DB state.
     *
     * @return an {@link Integer} representing the DB state of the revision
     */
    public Integer getScanStateForDB() {
        if (isInfected) {
            return AV_SCAN_INFECTED_DB_STATE;
        } else if (isEncrypted) {
            return AV_SCAN_ENCRYPTED_DB_STATE;
        } else if (isWarning) {
            return AV_SCAN_WARNING_DB_STATE;
        } else if (isError) {
            return AV_SCAN_ERROR_DB_STATE;
        } else {
            return AV_SCAN_NOT_INFECTED_DB_STATE;
        }
    }

    /**
     * @return the dId
     */
    public String getdId() {
        return dId;
    }

    /**
     * @param dId
     *            the dId to set
     */
    public void setdId(String dId) {
        this.dId = dId;
    }

    /**
     * @return the lastScanned
     */
    public Date getLastScanned() {
        return lastScanned;
    }

    /**
     * @param lastScanned
     *            the lastScanned to set
     */
    public void setLastScanned(Date lastScanned) {
        this.lastScanned = lastScanned;
    }

    /**
     * @return the scanStatusMessage
     */
    public String getScanStatusMessage() {
        return scanStatusMessage;
    }

    /**
     * @param scanStatusMessage
     *            the scanStatusMessage to set
     */
    public void setScanStatusMessage(String scanStatusMessage) {
        this.scanStatusMessage = scanStatusMessage;
    }

    /**
     * @return the scanTimes
     */
    public int getScanTimes() {
        return scanTimes;
    }

    /**
     * @param scanTimes
     *            the scanTimes to set
     */
    public void setScanTimes(int scanTimes) {
        this.scanTimes = scanTimes;
    }

    /**
     * @return the isNotScanned
     */
    public boolean isError() {
        return isError;
    }

    /**
     * @param isError
     *            the isNotScanned to set
     */
    public void setError(boolean isError) {
        this.isError = isError;
    }

    /**
     * @return the isEncrypted
     */
    public boolean isEncrypted() {
        return isEncrypted;
    }

    /**
     * @param isEncrypted
     *            the isEncrypted to set
     */
    public void setEncrypted(boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
    }

    /**
     * @return the isInfected
     */
    public boolean isInfected() {
        return isInfected;
    }

    /**
     * @param isInfected
     *            the isInfected to set
     */
    public void setInfected(boolean isInfected) {
        this.isInfected = isInfected;
    }

    /**
     * @return the isWarning
     */
    public boolean isWarning() {
        return isWarning;
    }

    /**
     * @param isWarning
     *            the isWarning to set
     */
    public void setWarning(boolean isWarning) {
        this.isWarning = isWarning;
    }

    public void resetFlags() {
        setEncrypted(false);
        setError(false);
        setInfected(false);
        setWarning(false);

    }
}
