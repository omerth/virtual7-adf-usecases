package com.eurofighter.fileupload.util;


import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidad.util.ClassLoaderUtils;


/**
 * Utility class for handeling request objects.
 */
public class RequestUtils {

    /**
     * Private logger.
     */
    private static final ADFLogger LOG = ADFLogger.createADFLogger(RequestUtils.class);

    /**
     * Private constructor to avoid instantiation as this is a utility class.
     */
    private RequestUtils() {
        super();
    }

    /**
     * Method for obtaining initialization parmateres for a request context (eyther servlet or portlet request context).
     *
     * @param context the context.
     * @param parameterName the initialization parameter name.
     * @return a String containing the value of the intialization parameter or null in case the parameter has no value set
     *      or there was an error retrieveing its value.
     */
    public static String getInitParameter(Object context, String parameterName) {
        String paramValue = null;
        if (isServletContextClass(context)) {
            paramValue = getInitParameterForServletContext(context, parameterName);
        } else if (isPortletContextClass(context)) {
            paramValue = getInitParameterForPortletContext(context, parameterName);
        }
        return paramValue;
    }

    /**
     * Method for getting an attirbute from a request (eyther servlet or portlet request).
     *
     * @param request the request.
     * @param attributeName the name of the attribute to obtain from request.
     * @return the value of the attribute from request or null in case the attribute is not set or has null value.
     */
    public static Object getAttribute(Object request, String attributeName) {
        Object attrVal = null;
        if (isServletRequestClass(request)) {
            attrVal = getAttributeForServletRequest(request, attributeName);
        } else if (isPortletRequestClass(request)) {
            attrVal = getAttributeForPortletRequest(request, attributeName);
        }
        return attrVal;
    }

    /**
     * Method for setting an attirbute to a request (eyther servlet or portlet request).
     *
     * @param request the request.
     * @param attributeName the name of the attribute to set in request.
     * @param value the value for the attribute.
     */
    public static void setAttribute(Object request, String attributeName, Object value) {
        if (isServletRequestClass(request)) {
            setAttributeForServletRequest(request, attributeName, value);
        } else if (isPortletRequestClass(request)) {
            setAttributeForPortletRequest(request, attributeName, value);
        }
    }

    // ------------ START Servlet related functionality ------------------

    private static boolean isServletContextClass(Object context) {
        return ServletContext.class.isInstance(context);
    }

    private static boolean isServletRequestClass(Object request) {
        return ServletRequest.class.isInstance(request);
    }

    private static String getInitParameterForServletContext(Object context, String parameterName) {
        return ((ServletContext)context).getInitParameter(parameterName);
    }

    private static Object getAttributeForServletRequest(Object request, String attributeName) {
        return ((ServletRequest)request).getAttribute(attributeName);
    }

    private static void setAttributeForServletRequest(Object request, String attributeName, Object value) {
        ((ServletRequest)request).setAttribute(attributeName, value);
    }

    // ------------ END Servlet related functionality ------------------

    // ------------ START Portlet related functionality ------------------

    /**
     * Class instances for portlet requests.
     */
    private static final Class<?> _PORTLET_CONTEXT_CLASS;
    private static final Class<?> _PORTLET_REQUEST_CLASS;

    static {
        Class<?> context;
        Class<?> request;

        try {
            context = ClassLoaderUtils.loadClass("javax.portlet.PortletContext");
            request = ClassLoaderUtils.loadClass("javax.portlet.PortletRequest");
        } catch (final ClassNotFoundException e) {
            LOG.fine("Portlet API is not available on the classpath. Portlet configurations are disabled.");
            context = null;
            request = null;
        }

        _PORTLET_CONTEXT_CLASS = context;
        _PORTLET_REQUEST_CLASS = request;
    }


    private static boolean isPortletContextClass(Object context) {
        return (_PORTLET_CONTEXT_CLASS != null && _PORTLET_CONTEXT_CLASS.isInstance(context));
    }

    private static boolean isPortletRequestClass(Object request) {
        return (_PORTLET_REQUEST_CLASS != null && _PORTLET_REQUEST_CLASS.isInstance(request));
    }

    private static String getInitParameterForPortletContext(Object context, String parameterName) {
        try {
            Method getInitParamMethod = _PORTLET_CONTEXT_CLASS.getMethod("getInitParameter", String.class);
            if (getInitParamMethod != null) {
                String val = (String)getInitParamMethod.invoke(context, parameterName);
                return val;
            }
        } catch (Exception e) {
            LOG.warning("Exception while invoking the getInitParameterForPortletContext() method for portlet context!");
        }

        return null;
    }

    private static Object getAttributeForPortletRequest(Object request, String attributeName) {
        try {
            Method getAttributeMethod = _PORTLET_CONTEXT_CLASS.getMethod("getAttribute", String.class);
            if (getAttributeMethod != null) {
                Object val = getAttributeMethod.invoke(request, attributeName);
                return val;
            }
        } catch (Exception e) {
            LOG.warning("Exception while invoking the getAttributeForPortletRequest() method for portlet context!");
        }

        return null;
    }

    private static void setAttributeForPortletRequest(Object request, String attributeName, Object value) {
        try {
            Method setAttributeMethod = _PORTLET_CONTEXT_CLASS.getMethod("setAttribute", String.class, Object.class);
            if (setAttributeMethod != null) {
                setAttributeMethod.invoke(request, attributeName, value);
            }
        } catch (Exception e) {
            LOG.warning("Exception while invoking the setAttributeForPortletRequest() method for portlet context!");
        }
    }

    // ------------ END Portlet related functionality ------------------
}
