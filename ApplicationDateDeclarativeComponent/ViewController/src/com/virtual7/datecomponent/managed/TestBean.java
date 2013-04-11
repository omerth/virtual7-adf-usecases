package com.virtual7.datecomponent.managed;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;


public class TestBean {

    private String datePattern;
    private List secondaryDatePatterns;

    private Map<String, Boolean> errorFlags;
    private Map<String, String> errorTexts;

    public TestBean() {
        super();

        this.datePattern = "dd.MM.yyyy";

        this.secondaryDatePatterns = new ArrayList();
        this.secondaryDatePatterns.add("dd/MM/yyyy");
        this.secondaryDatePatterns.add("dd-MM-yyyy");

        errorFlags = new HashMap<String, Boolean>();
        errorTexts = new HashMap<String, String>();
    }

    public void onSubmit(ActionEvent actionEvent) {
        // Perform the validations, and everything will be re-rendered after this action as the button is not partialSubmit.

        // First remove all validation flags.
        this.errorFlags.clear();
        this.errorTexts.clear();

        // Component 1: Date pattern validation.
        String comp1Id = getComp1Id();
        String val1 = getVal1();
        if (val1 != null && !"".equals(val1)) {
            Date d1 = parseStringToDate(val1, getDatePattern(), getSecondaryDatePatterns());
            if (d1 == null) {
                // Invalid date pattern.
                this.errorFlags.put(comp1Id, true);
                this.errorTexts.put(comp1Id, "Invalid date given in the component 1! The date pattern is not ok!");
            }
        }

        // Component 2: Date pattern validation and min/max validation.
        String comp2Id = getComp2Id();
        String val2 = getVal2();
        if (val2 != null && !"".equals(val2)) {
            Date d2 = parseStringToDate(val2, getDatePattern(), getSecondaryDatePatterns());
            if (d2 == null) {
                // Invalid date pattern.
                this.errorFlags.put(comp2Id, true);
                this.errorTexts.put(comp2Id, "Invalid date given in the component 2! The date pattern is not ok!");
            } else {
                // Min max validation.
                Calendar cal = getNormalizedCalendar(d2);

                Calendar min = Calendar.getInstance();
                min.setTime(getMinValue());
                if (cal.before(min)) {
                    // Date after the min
                    this.errorFlags.put(comp2Id, true);
                    this.errorTexts.put(comp2Id,
                                        "Invalid date given in the component 2! The date is before the min date!");
                }

                Calendar max = Calendar.getInstance();
                max.setTime(getMaxValue());
                if (cal.after(max)) {
                    // Date after the min
                    this.errorFlags.put(comp2Id, true);
                    this.errorTexts.put(comp2Id,
                                        "Invalid date given in the component 2! The date is after the max date!");
                }
            }
        }

        // Range component: Date pattern validation.
        String compRangeId = getRangeCompFromId();
        StringBuilder errorTxt = new StringBuilder();
        boolean valid = true;

        String valFrom = getValRangeCompFrom();
        Date dateFrom = null;
        if (valFrom != null && !"".equals(valFrom)) {
            dateFrom = parseStringToDate(valFrom, getDatePattern(), getSecondaryDatePatterns());
            if (dateFrom == null) {
                // Invalid date pattern.
                valid = false;
                errorTxt.append("The date pattern for the from date is not ok!");
            }
        }

        String valTo = getValRangeCompTo();
        Date dateTo = null;
        if (valTo != null && !"".equals(valTo)) {
            dateTo = parseStringToDate(valTo, getDatePattern(), getSecondaryDatePatterns());
            if (dateTo == null) {
                // Invalid date pattern.
                valid = false;
                errorTxt.append("The date pattern for the to date is not ok!");
            }
        }

        if (!valid) {
            this.errorFlags.put(compRangeId, true);
            this.errorTexts.put(compRangeId, "Invalid date given in the range component! " + errorTxt.toString());
        } else {
            // Validate if from is before to.
            if (dateFrom != null && dateTo != null) {
                Calendar calFrom = getNormalizedCalendar(dateFrom);
                Calendar calTo = getNormalizedCalendar(dateTo);
                if (calFrom.after(calTo)) {
                    this.errorFlags.put(compRangeId, true);
                    this.errorTexts.put(compRangeId,
                                        "Invalid date given in the range component! The date from is after the date to!");
                }
            }
        }
    }

    public Map<String, Boolean> getErrorFlags() {
        return errorFlags;
    }

    public Map<String, String> getErrorTexts() {
        return errorTexts;
    }

    /**
     * Try to parse a String into a Date, according to eyther the primary date format, or a list of secondary date formats.
     *
     * @param str the string.
     * @param primaryDateFormat the primary date format. This should not be null.
     * @param secondaryDateFormats the secondary date formats or null.
     */
    private static Date parseStringToDate(String str, String primaryDateFormat, List secondaryDateFormats) {
        // Validate parameter.
        if (str == null || "".equals(str)) {
            return null;
        }

        // Try to parse the string according to the primary date format.
        try {
            Date d = new SimpleDateFormat(primaryDateFormat).parse(str);
            return d;
        } catch (ParseException e) {
            // String can not be parsed according to the primary date format, so try according to the secondary date formats if there are any.
            if (secondaryDateFormats != null && secondaryDateFormats.size() > 0) {
                for (int i = 0; i < secondaryDateFormats.size(); i++) {
                    try {
                        String sdf = (String)secondaryDateFormats.get(i);
                        Date d = new SimpleDateFormat(sdf).parse(str);
                        return d;
                    } catch (ParseException e1) {
                        // String can not be parsed according to this secondary date format.
                    }
                }
            }
        }

        return null;
    }

    /**
     * Get a normalized calendar with hour, min, second and milisecond set at the beginning of the day.
     *
     * @param d the date.
     * @return a Calendar.
     */
    private static Calendar getNormalizedCalendar(Date d) {
        Calendar c = null;
        if (d != null) {
            c = Calendar.getInstance();
            c.setTime(d);
            c.set(Calendar.HOUR, c.getActualMinimum(Calendar.HOUR));
            c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
            c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
            c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
        }
        return c;
    }


    public Date getMinValue() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2013);
        c.set(Calendar.MONTH, Calendar.APRIL);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR, c.getActualMinimum(Calendar.HOUR));
        c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));

        return c.getTime();
    }

    public Date getMaxValue() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2013);
        c.set(Calendar.MONTH, Calendar.APRIL);
        c.set(Calendar.DAY_OF_MONTH, 25);
        c.set(Calendar.HOUR, c.getActualMaximum(Calendar.HOUR));
        c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));

        return c.getTime();
    }

    public String getDatePattern() {
        return datePattern;
    }

    public List getSecondaryDatePatterns() {
        return secondaryDatePatterns;
    }

    /**
     * Component 1
     */
    private String val1;

    public String getComp1Id() {
        return "comp1Id";
    }

    public void setVal1(String val1) {
        this.val1 = val1;
    }

    public String getVal1() {
        return val1;
    }

    /**
     * Component 2
     */
    private String val2;

    public String getComp2Id() {
        return "comp2Id";
    }

    public void setVal2(String val2) {
        this.val2 = val2;
    }

    public String getVal2() {
        return val2;
    }

    public void onChangeComp2(ValueChangeEvent valueChangeEvent) {
        System.out.println("The value of the component 2 was changed! New value:" + valueChangeEvent.getNewValue() +
                           ", oldValue:" + valueChangeEvent.getOldValue());
    }

    /**
     * Component 2
     */
    private String valRangeCompFrom;
    private String valRangeCompTo;

    public String getRangeCompFromId() {
        return "rangeCompFromId";
    }

    public String getRangeCompToId() {
        return "rangeCompToId";
    }

    public void onChangeRangeCompFrom(ValueChangeEvent valueChangeEvent) {
        System.out.println("The value of the FROM field of the range component was changed! New value:" +
                           valueChangeEvent.getNewValue() + ", oldValue:" + valueChangeEvent.getOldValue());
    }

    public void onChangeRangeCompTo(ValueChangeEvent valueChangeEvent) {
        System.out.println("The value of the TO field of the range component was changed! New value:" +
                           valueChangeEvent.getNewValue() + ", oldValue:" + valueChangeEvent.getOldValue());
    }

    public void setValRangeCompFrom(String valRangeCompFrom) {
        this.valRangeCompFrom = valRangeCompFrom;
    }

    public String getValRangeCompFrom() {
        return valRangeCompFrom;
    }

    public void setValRangeCompTo(String valRangeCompTo) {
        this.valRangeCompTo = valRangeCompTo;
    }

    public String getValRangeCompTo() {
        return valRangeCompTo;
    }
}
