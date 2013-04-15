package com.virtual7.datecomponent.comp;


import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;


/**
 * Component class.
 */
public class DateComponent extends ADateComponent {

    private static final String ATTRIBUTE_VALUE = "value";

    private static final String ATTRIBUTE_MIN_VALUE = "minValue";

    private static final String ATTRIBUTE_MAX_VALUE = "maxValue";

    private static final String METHOD_ATTRIBUTE_VALUE_CHANGE_LISTENER = "valueChangeListener";

    private Date dateVal;
    private String strVal;

    private RichInputText textInput;
    private RichInputDate dateInput;

    /**
     * Default constructor.
     */
    public DateComponent() {
    }

    /**
     * Value change listener called when the string input is changed.
     *
     * @param valueChangeEvent
     */
    public void onChangeStr(ValueChangeEvent valueChangeEvent) {
        String oldValue = (String)valueChangeEvent.getOldValue();
        String newValue = (String)valueChangeEvent.getNewValue();

        // Set string value.
        this.strVal = newValue;

        // Try to parse the String value to a date according to the date formats.
        String primaryDateFormat = getWrappedDateFormatAttr();
        List secondaryDateFormats = (List)getAttribute(ATTRIBUTE_SECONDARY_DATE_FORMATS);
        Date d = parseStringToDate(newValue, primaryDateFormat, secondaryDateFormats);

        // If we have a valid value then just check if we could set it in the date input, because if we don't and we set for example a value outside the min and max value, we will get errors from the component on submiting data.
        boolean didDateChange = false;
        if (d != null) {
            // Get the normalized date.
            Calendar cal = getNormalizedCalendar(d);

            // Only if the date respects some rules should we update the date value, otherwise the inputDate will complain and will show validation errors.
            // The min/max validations will also have to be replacicated by the custom error handeling mechanism, and pass to the error parameters the right messages.
            boolean ok = true;

            // 1. If date is greater or equal then the minDate.
            if (ok) {
                Date minDate = getWrappedMinValueAttr();
                if (minDate != null) {
                    Calendar minDateCal = getNormalizedCalendar(minDate);
                    if (cal.before(minDateCal)) {
                        // Invalid date.
                        ok = false;
                    }
                }
            }

            // 2. If date is smaller or equal then the maxDate.
            if (ok) {
                Date maxDate = getWrappedMaxValueAttr();
                if (maxDate != null) {
                    Calendar maxDateCal = getNormalizedCalendar(maxDate);
                    if (cal.after(maxDateCal)) {
                        // Invalid date.
                        ok = false;
                    }
                }
            }

            // Set date value only if all ok.
            if (ok) {
                this.dateVal = d;
                didDateChange = true;
            }
        }

        // Pass the value externally.
        super.externalizeValue(ATTRIBUTE_VALUE, METHOD_ATTRIBUTE_VALUE_CHANGE_LISTENER, getTextInput(), newValue,
                               oldValue);

        // In case we have set a valid date string then refresh the date input, otherwise reset it.
        if (didDateChange) {
            addPartialTarget(getDateInput());
        } else {
            this.dateVal = null;
            resetInput(getDateInput());
        }
    }

    /**
     * Value change listener called when date picker is used.
     *
     * @param valueChangeEvent
     */
    public void onChangeDate(ValueChangeEvent valueChangeEvent) {
        Date newValue = (Date)valueChangeEvent.getNewValue();
        String oldValue = this.strVal;

        this.dateVal = newValue;
        this.strVal = new SimpleDateFormat(getWrappedDateFormatAttr()).format(newValue);

        // Pass the value externally.
        super.externalizeValue(ATTRIBUTE_VALUE, METHOD_ATTRIBUTE_VALUE_CHANGE_LISTENER, getTextInput(), this.strVal,
                               oldValue);

        // Refresh the text input as well.
        addPartialTarget(getTextInput());

        // Set focus on the input text.
        setFocusOnComponent(getTextInput());
    }

    /**
     * Get the min value attribute in a normalized way, meaning that we consider the start of the day (hours, minutes, second, milisecond) of the given date.
     *
     * @return a Date object.
     */
    public Date getWrappedMinValueAttr() {
        Date val = (Date)getAttribute(ATTRIBUTE_MIN_VALUE);
        if (val != null) {
            return getNormalizedCalendar(val).getTime();
        }
        return null;
    }

    /**
     * Get the max value attribute in a normalized way, meaning that we consider the end of the day (hours, minutes, second, milisecond) of the given date.
     *
     * @return a Date object.
     */
    public Date getWrappedMaxValueAttr() {
        Date val = (Date)getAttribute(ATTRIBUTE_MAX_VALUE);
        if (val != null) {
            return getMaximizedCalendar(val).getTime();
        }
        return null;
    }

    public void setDateVal(Date dateVal) {
        // Rely only on the value change listeners to set values in the intrenal state of the bean, do not expect the framework to call the setters.
        //this.dateVal = dateVal;
    }

    public Date getDateVal() {
        return dateVal;
    }

    public void setStrVal(String strVal) {
        // Rely only on the value change listeners to set values in the intrenal state of the bean, do not expect the framework to call the setters.
        //this.strVal = strVal;
    }

    public String getStrVal() {
        return strVal;
    }

    public void setTextInput(RichInputText textInput) {
        this.textInput = textInput;
    }

    public RichInputText getTextInput() {
        return textInput;
    }

    public void setDateInput(RichInputDate dateInput) {
        this.dateInput = dateInput;
    }

    public RichInputDate getDateInput() {
        return dateInput;
    }
}
