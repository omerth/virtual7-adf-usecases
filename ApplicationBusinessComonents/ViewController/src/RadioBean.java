import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

public class RadioBean {
    private RichSelectOneChoice list;

    public RadioBean() {
    }

    public void radioChanged(ValueChangeEvent valueChangeEvent) {
        RichSelectOneChoice choice = getList();
        AdfFacesContext.getCurrentInstance().addPartialTarget(choice);
    }

    public void setList(RichSelectOneChoice list) {
        this.list = list;
    }

    public RichSelectOneChoice getList() {
        return list;
    }
}
