package com.virtual7.fileUpload.view.managed;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.myfaces.trinidad.model.UploadedFile;

public class InputFileBean {
    public InputFileBean() {
        super();
    }

    public void uploadFileValueChangeEvent(ValueChangeEvent valueChangeEvent) {

        // The event give access to an Uploade dFile which contains data about the file and its content
        UploadedFile file = (UploadedFile)valueChangeEvent.getNewValue();

        // Get the original file name
        String fileName = file.getFilename();

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = file.getInputStream();
            outputStream = new FileOutputStream(new File("d:\\tmp\\" + file.getFilename()));

            writeFile(inputStream, outputStream);
        } catch (IOException e) {
            popupMsg("Can not save the file on the server \n" +
                    e.getMessage());
        } finally {
            //we closing the streams
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ioe) {
                // TODO: Add catch code
                ioe.printStackTrace();
            }

        }
    }

    private void writeFile(InputStream inputStream, OutputStream outputStream) throws IOException {
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        outputStream.flush();
    }

    private void popupMsg(String msg) {
        FacesMessage fm = new FacesMessage(msg);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);
    }
}
