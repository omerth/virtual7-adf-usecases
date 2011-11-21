package com.virtual7.tableButton.view.managed;

import javax.faces.event.ActionEvent;

public class TableButtonBean {
    public TableButtonBean() {
        super();
    }

    public void buttonListener(ActionEvent actionEvent) {
        System.out.println("buttonListener called");
    }
}
