package com.virtual7.datecomponent.comp;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.el.ELContext;
import javax.el.MethodExpression;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.fragment.RichDeclarativeComponent;
import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.component.UIXEditableValue;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


abstract class ADateComponent extends RichDeclarativeComponent {

    public ADateComponent() {
        super();
    }


    //////////////////////////////////////////////////////////////////
    /////////// Functions related to declarative component ///////////
    //////////////////////////////////////////////////////////////////

    protected static final String ATTRIBUTE_SECONDARY_DATE_FORMATS = "secondaryDateFormats";

    protected static final String ATTRIBUTE_DATE_FORMAT = "dateFormat";
    protected static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";


    /**
     * Get the date format string passed to the component, or a default date format is not pattern is given.
     *
     * @return the date format string.
     */
    public String getWrappedDateFormatAttr() {
        String dateFormatAttr = (String)getAttribute(ATTRIBUTE_DATE_FORMAT);
        if (dateFormatAttr != null) {
            return dateFormatAttr;
        }
        return DEFAULT_DATE_FORMAT;
    }


    /**
     * Invoke the value change listener method if defined on the current component. The attribute with the name valueChangeListenerAttrName must be of type javax.el.MethodExpression.
     *
     * @param valueChangeListenerAttrName the method name which is defined as value change listener.
     * @param cmp the component on which the value change event is triggered.
     * @param oldValue the old value.
     * @param newValue the new value.
     */
    protected void invokeValueChangeListener(String valueChangeListenerAttrName, UIComponent cmp, Object oldValue,
                                             Object newValue) {
        MethodExpression valueChangeListener = getAttributeAsMethodExpression(valueChangeListenerAttrName);
        if (valueChangeListener != null && cmp != null) {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();

            ValueChangeEvent evt = new ValueChangeEvent(cmp, oldValue, newValue);
            Object[] params = new Object[] { evt };
            valueChangeListener.invoke(elContext, params);
        }
    }

    /**
     * Get an attribute as MethodExpression object. If the attribute is null or not of this type return null.
     *
     * @param attrName the attribute name.
     * @return an MethodExpression object or null.
     */
    protected MethodExpression getAttributeAsMethodExpression(String attrName) {
        Object attr = getAttribute(attrName);
        if (attr != null && MethodExpression.class.isInstance(attr)) {
            return (MethodExpression)attr;
        }
        return null;
    }

    /**
     * Externalize the value to the consumers of this date component. This will set the value attribute which will further
     * propagate the value to whatever reference was passed to the component as value and it will invoke the value change listener.
     *
     * @param newValue the value to set.
     * @param oldValue the old value of the input passed to the value change event.
     */
    protected void externalizeValue(String attributeName, String valueChangeListenerName, UIComponent comp,
                                    String newValue, String oldValue) {
        setAttribute(attributeName, newValue);

        // If there is a value change listener given then invoke it.
        invokeValueChangeListener(valueChangeListenerName, comp, oldValue, newValue);
    }

    /**
     * Add JavaScript to page to set focus to a component.
     *
     * @param comp the component on which to focus.
     */
    protected void setFocusOnComponent(UIComponent comp) {
        if (comp != null) {
            FacesContext fctx = FacesContext.getCurrentInstance();
            String clientId = comp.getClientId(fctx);

            // Compose JavaScript to be executed on the client. Use client id to ensure component is found if located innaming container.
            StringBuilder script = new StringBuilder();
            script.append("var textInput = ");
            script.append("AdfPage.PAGE.findComponentByAbsoluteId");
            script.append("('" + clientId + "');");
            script.append("if(textInput != null){");
            script.append("textInput.focus();");
            script.append("}");

            // Invoke JavaScript.
            writeJavaScriptToClient(script.toString());
        }
    }

    /**
     * Generic, reusable helper method to call JavaScript on a client
     *
     * @param script the script to write.
     */
    private void writeJavaScriptToClient(String script) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        erks.addScript(fctx, script);
    }

    /////////////////////////////////////////////////
    /////////// Generic utility functions ///////////
    /////////////////////////////////////////////////

    /**
     * Get a normalized calendar with hour, min, second and milisecond set at the beginning of the day.
     *
     * @param d the date.
     * @return a Calendar.
     */
    protected static Calendar getNormalizedCalendar(Date d) {
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

    /**
     * Get a maximizeed calendar with hour, min, second and milisecond set at the end of the day.
     *
     * @param d the date.
     * @return a Calendar.
     */
    protected static Calendar getMaximizedCalendar(Date d) {
        Calendar c = null;
        if (d != null) {
            c = Calendar.getInstance();
            c.setTime(d);
            c.set(Calendar.HOUR, c.getActualMaximum(Calendar.HOUR));
            c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
            c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
            c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
        }
        return c;
    }

    /**
     * Try to parse a String into a Date, according to eyther the primary date format, or a list of secondary date formats.
     *
     * @param str the string.
     * @param primaryDateFormat the primary date format. This should not be null.
     * @param secondaryDateFormats the secondary date formats or null.
     */
    protected static Date parseStringToDate(String str, String primaryDateFormat, List secondaryDateFormats) {
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
     * Add partial target on a component so that the view gets refreshed.
     *
     * @param comp the component.
     */
    protected static void addPartialTarget(UIComponent comp) {
        if (comp != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(comp);
        }
    }

    /**
     * Reset an input and refresh its view.
     *
     * @param input the input to reset.
     */
    protected static void resetInput(UIXEditableValue input) {
        if (input != null) {
            input.resetValue();
            addPartialTarget(input);
        }
    }
}
