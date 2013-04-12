package com.virtual7.adf.view;

import java.util.Date;

import javax.faces.event.ValueChangeEvent;

public class ValueChangeListenerMB {

    private String inputValue;
    private Date dateValue;

    public ValueChangeListenerMB() {
        super();
    }

    public void inputTextValueChangeListener(ValueChangeEvent valueChangeEvent) {
        System.out.println("ValueChangeListener of the input text was correctly invoked! NewValue:" +
                           valueChangeEvent.getNewValue() + ", OldValue:" + valueChangeEvent.getOldValue());
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    public Date getDateValue() {
        return dateValue;
    }
}
