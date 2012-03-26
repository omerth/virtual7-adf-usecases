

import com.virtual7.util.view.ADFUtils;
import com.virtual7.util.view.JSFUtils;

import javax.faces.event.ActionEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;


public class PostChangeBean {
    public PostChangeBean() {
    }

    public void emailChangeListener(ActionEvent actionEvent) {
        ApplicationModule appModule = ADFUtils.getDCBindingContainer().getDataControl().getApplicationModule();
        appModule.getTransaction().postChanges();
        JSFUtils.getFacesContext().getRenderResponse();
    }

    public void commitChangeListner(ActionEvent actionEvent) {
        OperationBinding op = ADFUtils.findOperation("Commit");
        op.execute();
        JSFUtils.getFacesContext().getRenderResponse();
    }
}
