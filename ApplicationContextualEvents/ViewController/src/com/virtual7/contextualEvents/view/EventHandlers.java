package com.virtual7.contextualEvents.view;

public class EventHandlers {
    public EventHandlers() {
        super();
    }
    
    public String handleEmailEvent(Object emailPayload) {
        return (String) emailPayload;
    }
    
    public String handleLastNamePayload(Object lastNamePayload) {
        return (String) lastNamePayload;
    }
}
