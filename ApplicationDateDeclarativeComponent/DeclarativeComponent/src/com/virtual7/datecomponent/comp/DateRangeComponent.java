package com.virtual7.datecomponent.comp;


import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;


public class DateRangeComponent extends ADateComponent {

    private static final String ATTRIBUTE_VALUE_FROM = "valueFrom";
    private static final String ATTRIBUTE_VALUE_TO = "valueTo";

    private static final String METHOD_ATTRIBUTE_VALUE_CHANGE_LISTENER_FROM = "valueChangeListenerFrom";
    private static final String METHOD_ATTRIBUTE_VALUE_CHANGE_LISTENER_TO = "valueChangeListenerTo";

    private Date dateValFrom;
    private String strValFrom;

    private RichInputText textInputFrom;
    private RichInputDate dateInputFrom;

    private Date dateValTo;
    private String strValTo;

    private RichInputText textInputTo;
    private RichInputDate dateInputTo;

    public DateRangeComponent() {
        super();
    }

    /**
     * Value change listener called when the string input is changed.
     *
     * @param valueChangeEvent
     */
    public void onChangeStrFrom(ValueChangeEvent valueChangeEvent) {
        String oldValue = (String)valueChangeEvent.getOldValue();
        String newValue = (String)valueChangeEvent.getNewValue();

        // Set string value.
        this.strValFrom = newValue;

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

            // 1. If date is smaller or equal then the dateTo.
            if (ok) {
                Date maxDate = this.dateValTo;
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
                this.dateValFrom = d;
                didDateChange = true;
            }
        }

        // Pass the value externally.
        super.externalizeValue(ATTRIBUTE_VALUE_FROM, METHOD_ATTRIBUTE_VALUE_CHANGE_LISTENER_FROM, getTextInputFrom(),
                               newValue, oldValue);

        // In case we have set a valid date string then refresh the date input, otherwise reset it.
        if (didDateChange) {
            addPartialTarget(getDateInputFrom());
        } else {
            this.dateValFrom = null;
            resetInput(getDateInputFrom());
        }

        // Refresh also the dateTo input as the min value should be adapted.
        addPartialTarget(getDateInputTo());
    }

    /**
     * Value change listener called when date picker is used.
     *
     * @param valueChangeEvent
     */
    public void onChangeDateFrom(ValueChangeEvent valueChangeEvent) {
        Date newValue = (Date)valueChangeEvent.getNewValue();
        String oldValue = this.strValFrom;

        this.dateValFrom = newValue;
        this.strValFrom = new SimpleDateFormat(getWrappedDateFormatAttr()).format(newValue);

        // Pass the value externally.
        super.externalizeValue(ATTRIBUTE_VALUE_FROM, METHOD_ATTRIBUTE_VALUE_CHANGE_LISTENER_FROM, getTextInputFrom(),
                               this.strValFrom, oldValue);

        // Refresh the text input as well.
        addPartialTarget(getTextInputFrom());

        // Refresh also the dateTo input as the min value should be adapted.
        addPartialTarget(getDateInputTo());
    }

    /**
     * Value change listener called when the string input is changed.
     *
     * @param valueChangeEvent
     */
    public void onChangeStrTo(ValueChangeEvent valueChangeEvent) {
        String oldValue = (String)valueChangeEvent.getOldValue();
        String newValue = (String)valueChangeEvent.getNewValue();

        // Set string value.
        this.strValTo = newValue;

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

            // 1. If date is greater or equal then the fromDate.
            if (ok) {
                Date minDate = this.dateValFrom;
                if (minDate != null) {
                    Calendar minDateCal = getNormalizedCalendar(minDate);
                    if (cal.before(minDateCal)) {
                        // Invalid date.
                        ok = false;
                    }
                }
            }

            // Set date value only if all ok.
            if (ok) {
                this.dateValTo = d;
                didDateChange = true;
            }
        }

        // Pass the value externally.
        super.externalizeValue(ATTRIBUTE_VALUE_TO, METHOD_ATTRIBUTE_VALUE_CHANGE_LISTENER_TO, getTextInputTo(),
                               newValue, oldValue);

        // In case we have set a valid date string then refresh the date input, otherwise reset it.
        if (didDateChange) {
            addPartialTarget(getDateInputTo());
        } else {
            this.dateValTo = null;
            resetInput(getDateInputTo());
        }

        // Refresh also the dateFrom input as the min value should be adapted.
        addPartialTarget(getDateInputFrom());
    }

    /**
     * Value change listener called when date picker is used.
     *
     * @param valueChangeEvent
     */
    public void onChangeDateTo(ValueChangeEvent valueChangeEvent) {
        Date newValue = (Date)valueChangeEvent.getNewValue();
        String oldValue = this.strValTo;

        this.dateValTo = newValue;
        this.strValTo = new SimpleDateFormat(getWrappedDateFormatAttr()).format(newValue);

        // Pass the value externally.
        super.externalizeValue(ATTRIBUTE_VALUE_TO, METHOD_ATTRIBUTE_VALUE_CHANGE_LISTENER_TO, getTextInputTo(),
                               this.strValTo, oldValue);

        // Refresh the text input as well.
        addPartialTarget(getTextInputTo());

        // Refresh also the dateFrom input as the min value should be adapted.
        addPartialTarget(getDateInputFrom());
    }

    public void setDateValFrom(Date dateValFrom) {
        // Rely only on the value change listeners to set values in the intrenal state of the bean, do not expect the framework to call the setters.
        //this.dateValFrom = dateValFrom;
    }

    public Date getDateValFrom() {
        return dateValFrom;
    }

    public void setStrValFrom(String strValFrom) {
        // Rely only on the value change listeners to set values in the intrenal state of the bean, do not expect the framework to call the setters.
        //this.strValFrom = strValFrom;
    }

    public String getStrValFrom() {
        return strValFrom;
    }

    public void setTextInputFrom(RichInputText textInputFrom) {
        this.textInputFrom = textInputFrom;
    }

    public RichInputText getTextInputFrom() {
        return textInputFrom;
    }

    public void setDateInputFrom(RichInputDate dateInputFrom) {
        this.dateInputFrom = dateInputFrom;
    }

    public RichInputDate getDateInputFrom() {
        return dateInputFrom;
    }

    public void setDateValTo(Date dateValTo) {
        // Rely only on the value change listeners to set values in the intrenal state of the bean, do not expect the framework to call the setters.
        //this.dateValTo = dateValTo;
    }

    public Date getDateValTo() {
        return dateValTo;
    }

    public void setStrValTo(String strValTo) {
        // Rely only on the value change listeners to set values in the intrenal state of the bean, do not expect the framework to call the setters.
        //this.strValTo = strValTo;
    }

    public String getStrValTo() {
        return strValTo;
    }

    public void setTextInputTo(RichInputText textInputTo) {
        this.textInputTo = textInputTo;
    }

    public RichInputText getTextInputTo() {
        return textInputTo;
    }

    public void setDateInputTo(RichInputDate dateInputTo) {
        this.dateInputTo = dateInputTo;
    }

    public RichInputDate getDateInputTo() {
        return dateInputTo;
    }
}
