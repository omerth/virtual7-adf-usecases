package com.virtual7.adf.view;

import java.util.Date;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;

public class JSNotLoadedForInputDate {

    private Date date1;
    private Date date2;
    private RichInputText inputText;

    public JSNotLoadedForInputDate() {
        super();
    }

    public Date getMinDate() {
        return new Date();
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Date getDate2() {
        return date2;
    }

    public void renderInputText(ActionEvent actionEvent) {
        getInputText().setRendered(true);
    }

    public void setInputText(RichInputText inputText) {
        this.inputText = inputText;
    }

    public RichInputText getInputText() {
        return inputText;
    }
}
