package com.virtual7.stampBinding.view.managed;

import java.io.OutputStream;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

public class ManagedBean {
	private RichCommandButton button;
	private RichCommandButton button1;

	public ManagedBean() {
		super();
	}

	public void setButton(RichCommandButton button) {
		this.button = button;
	}

	public RichCommandButton getButton() {
		return button;
	}

	public void setButton1(RichCommandButton button1) {
		this.button1 = button1;
	}

	public RichCommandButton getButton1() {
		return button1;
	}

	public void downloadHandler(FacesContext facesContext, OutputStream outputStream) {
		System.out.println("table empId:" + button.getAttributes().get("empId"));
	}

	public void downloadHandler1(FacesContext facesContext, OutputStream outputStream) {
		System.out.println("iterator empId:" + button.getAttributes().get("empId"));
	}
}
