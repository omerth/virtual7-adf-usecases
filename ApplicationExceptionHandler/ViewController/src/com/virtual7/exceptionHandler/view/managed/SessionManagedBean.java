package com.virtual7.exceptionHandler.view.managed;

public class SessionManagedBean {

    private final String EXCEPTION_HANDLE_TYPE_ERROR_MESSAGE = "ERROR_MESSAGE";

    private final String EXCEPTION_HANDLE_TYPE_ERROR_PAGE = "ERROR_PAGE";

    private String exceptionHandleType;

    public SessionManagedBean() {
    }

    public void setExceptionHandleType(String exceptionHandleType) {
        this.exceptionHandleType = exceptionHandleType;
    }

    public String getExceptionHandleType() {
        if(exceptionHandleType == null){
            setExceptionHandleType(EXCEPTION_HANDLE_TYPE_ERROR_MESSAGE);
        }
        return exceptionHandleType;
    }
    
    public String getTypeErrorMessage(){
        return EXCEPTION_HANDLE_TYPE_ERROR_MESSAGE;
    }
    
    public String getTypeErrorPage(){
        return EXCEPTION_HANDLE_TYPE_ERROR_PAGE;
    }
}
