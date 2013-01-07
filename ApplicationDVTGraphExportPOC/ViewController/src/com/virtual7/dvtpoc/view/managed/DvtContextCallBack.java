package com.virtual7.dvtpoc.view.managed;

import java.awt.Color;
import java.awt.Dimension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStream;

import java.util.GregorianCalendar;

import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.graph.Background;
import oracle.adf.view.faces.bi.component.graph.UIGraph;

import oracle.dss.dataView.ImageView;

public class DvtContextCallBack implements ContextCallback {
    public DvtContextCallBack() {
        super();
    }

    private String fileName = null;

    public DvtContextCallBack(String fileName) {
        this();

        this.fileName = fileName;
    }

    private OutputStream out = null;

    public DvtContextCallBack(OutputStream out) {
        this();

        this.out = out;
    }

    //method called on each component that matches the search criteria

    public void invokeContextCallback(FacesContext facesContext, UIComponent uiComponent) {
        //PRINT
        ////Only care for instances of UIGraph
        if (uiComponent instanceof UIGraph) {
            UIGraph dvtgraph = (UIGraph)uiComponent;
            //We can set a different background color. However,
            //this changes the graph instance and thus we need to
            //copy and set back the current values
            Background orgBackground = dvtgraph.getBackground();
            //create and set a new background
            Background bg = new Background();
            //backgrounds can not only be set to colors but also transparent
            //with graduated fill
            bg.setFillColor(Color.WHITE);
            //explicitly set transparent fill to false for a white
            //background
            bg.setFillTransparent(false);
            dvtgraph.setBackground(bg);
            //this needs to be called to ensure the background color is set.
            dvtgraph.transferProperties();
            //image view is what we want to export. Also, this is where we
            //set the exported image size
            ImageView imgView = dvtgraph.getImageView();
            //We can set a different image size. However, this changes the
            //graph instance and thus best is to copy the current values to
            //set them back after the image is processed
            Dimension orgDimension = imgView.getImageSize();
            //width, height
            imgView.setImageSize(new Dimension(400, 400));
            //Get the OS specific file path separator
            String slash = File.separator;
            String dSlash = slash + slash;
            //create a unique file name (you may want to change generating the
            //file name using a real random so that concurrent access to the
            //application don't conflict if they are processed just in the
            //same fraction of a second
            if (out == null) {
                String fullFilePath;
                if (this.fileName == null) {
                    String filename = GregorianCalendar.getInstance().getTimeInMillis() + "dvt";
                    String drive = "d:";
                    String tmpFolder = "dvt";
                    fullFilePath = drive + dSlash + tmpFolder + slash + filename + ".png";
                } else {
                    fullFilePath = this.fileName;
                }

                File file = null;
                FileOutputStream fos;
                try {
                    file = new File(fullFilePath);
                    fos = new FileOutputStream(file);
                    imgView.exportToPNG(fos);
                    fos.close();
                } catch (FileNotFoundException e) {
                    //For sample - just show stack trace
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //reset the graph default – IMPORTANT ! – as otherwise the
                    //web instance of the graph would change with the next refresh
                    //
                    dvtgraph.setBackground(orgBackground);
                    dvtgraph.transferProperties();
                    imgView.setImageSize(orgDimension);
                }
            } else {
                imgView.exportToPNG(out);
            }

        }
    }
}
