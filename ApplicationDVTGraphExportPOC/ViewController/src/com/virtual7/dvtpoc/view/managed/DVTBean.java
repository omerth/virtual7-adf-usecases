package com.virtual7.dvtpoc.view.managed;


import com.virtual7.dvtpoc.pdf.FOPPDFGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.sql.SQLException;

import javax.el.ExpressionFactory;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.faces.bi.event.ClickEvent;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.dss.dataView.Attributes;
import oracle.dss.dataView.ComponentHandle;
import oracle.dss.dataView.DataComponentHandle;
import oracle.dss.util.ComponentInfo;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.component.UIXSwitcher;


public class DVTBean {

    private String faceName;
    private UIXSwitcher switcher;
    private UIGraph yearGraph;
    private UIGraph monthGraph;

    private Number selectedYear = null;

    public DVTBean() {
        super();

        this.faceName = "year";
    }

    public void yearClick(ClickEvent clickEvent) {
        ComponentHandle componentHandle = clickEvent.getComponentHandle();
        if (DataComponentHandle.class.isInstance(componentHandle)) {
            DataComponentHandle dhandle = (DataComponentHandle)componentHandle;

            // Get the value displayed in the series
            System.out.println("Value: " + dhandle.getValue(DataComponentHandle.UNFORMATTED_VALUE));

            // Get the series attributes
            Attributes[] seriesInfo = dhandle.getSeriesAttributes();
            if (seriesInfo != null) {
                for (Attributes attrs : seriesInfo) {
                    System.out.println("Series value: " + attrs.getValue(Attributes.LABEL_VALUE));
                    System.out.println("Series name: " + attrs.getValue(Attributes.LABEL_ATTRIBUTE));
                    System.out.println("Series value id: " + attrs.getValue(Attributes.ID_VALUE));
                    System.out.println("Series name id: " + attrs.getValue(Attributes.ID_ATTRIBUTE));
                }
            }
            // Get the group attributes
            Attributes[] groupInfo = dhandle.getGroupAttributes();
            if (groupInfo != null) {
                for (Attributes attrs : groupInfo) {
                    System.out.println("Group value: " + attrs.getValue(Attributes.LABEL_VALUE));
                    System.out.println("Group name: " + attrs.getValue(Attributes.LABEL_ATTRIBUTE));

                    String groupName = String.valueOf(attrs.getValue(Attributes.LABEL_ATTRIBUTE));
                    if ("Year".equals(groupName)) {
                        try {
                            this.selectedYear = new Number(String.valueOf(attrs.getValue(Attributes.LABEL_VALUE)));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        // for the selected year refresh the months view.
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding op = bindings.getOperationBinding("ExecuteWithParams");
        op.execute();

        // Switch to the month facet.
        this.faceName = "month";

        // Refresh.
        AdfFacesContext.getCurrentInstance().addPartialTarget(switcher);
    }

    public void setFaceName(String faceName) {
        this.faceName = faceName;
    }

    public String getFaceName() {
        return faceName;
    }

    public void setSwitcher(UIXSwitcher switcher) {
        this.switcher = switcher;
    }

    public UIXSwitcher getSwitcher() {
        return switcher;
    }

    public void setYearGraph(UIGraph yearGraph) {
        this.yearGraph = yearGraph;
    }

    public UIGraph getYearGraph() {
        return yearGraph;
    }

    public void setMonthGraph(UIGraph monthGraph) {
        this.monthGraph = monthGraph;
    }

    public UIGraph getMonthGraph() {
        return monthGraph;
    }

    public void downloadYearJPG(FacesContext facesContext, OutputStream outputStream) {
        UIViewRoot root = facesContext.getViewRoot();
        root.invokeOnComponent(facesContext, getYearGraph().getClientId(facesContext),
                               new DvtContextCallBack(outputStream));
    }

    public void downloadYearPDF(FacesContext facesContext, OutputStream outputStream) {
        File tmpFile = null;
        OutputStream out = null;
        try {
            // Create png.
            tmpFile = File.createTempFile("yearGraph", "png");
            out = new FileOutputStream(tmpFile);
            downloadYearJPG(facesContext, out);

            // Create PDF.
            StringBuffer xml = new StringBuffer();
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xml.append("<ROOT>");
            xml.append("<GRAPH_IMG>" + tmpFile.getAbsolutePath() + "</GRAPH_IMG>");
            xml.append("</ROOT>");

            FOPPDFGenerator.generatePDF(xml.toString(), "pdf.xsl", outputStream);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            // Close output stream.
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            // Delete the temp file.
            if (tmpFile != null) {
                tmpFile.delete();
            }
        }
    }

    public void backToYear(ActionEvent actionEvent) {
        this.faceName = "year";
        AdfFacesContext.getCurrentInstance().addPartialTarget(switcher);
    }

    public void downloadMonthJPG(FacesContext facesContext, OutputStream outputStream) {
        UIViewRoot root = facesContext.getViewRoot();
        root.invokeOnComponent(facesContext, getMonthGraph().getClientId(facesContext),
                               new DvtContextCallBack(outputStream));
    }


    public void downloadMonthPDF(FacesContext facesContext, OutputStream outputStream) {
        File tmpFile = null;
        OutputStream out = null;
        try {
            // Create png.
            tmpFile = File.createTempFile("monthGraph", "png");
            out = new FileOutputStream(tmpFile);
            downloadMonthJPG(facesContext, out);

            // Create PDF.
            StringBuffer xml = new StringBuffer();
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xml.append("<ROOT>");
            xml.append("<GRAPH_IMG>" + tmpFile.getAbsolutePath() + "</GRAPH_IMG>");
            xml.append("</ROOT>");

            FOPPDFGenerator.generatePDF(xml.toString(), "pdf.xsl", outputStream);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            // Close output stream.
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            // Delete the temp file.
            if (tmpFile != null) {
                tmpFile.delete();
            }
        }
    }

    public void setSelectedYear(Number selectedYear) {
        this.selectedYear = selectedYear;
    }

    public Number getSelectedYear() {
        return selectedYear;
    }
}
