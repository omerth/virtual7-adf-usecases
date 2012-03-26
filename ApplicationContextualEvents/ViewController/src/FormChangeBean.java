import java.util.HashMap;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import oracle.adf.model.BindingContext;  
import oracle.binding.BindingContainer;  
import oracle.jbo.uicli.binding.JUEventBinding; 

public class FormChangeBean {
    private String lastName;

    public FormChangeBean() {
    }

    public void lastNameChanged(ValueChangeEvent valueChangeEvent) {
        BindingContainer bindingContainer = BindingContext.getCurrent().getCurrentBindingsEntry();
        JUEventBinding eventBinding = (JUEventBinding)bindingContainer.get("lastNameChangedEventBinding");
        ValueChangeListener valueChangeListener = (ValueChangeListener)eventBinding.getListener();
        setLastName((String)valueChangeEvent.getNewValue());
        valueChangeListener.processValueChange(valueChangeEvent);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }
}
