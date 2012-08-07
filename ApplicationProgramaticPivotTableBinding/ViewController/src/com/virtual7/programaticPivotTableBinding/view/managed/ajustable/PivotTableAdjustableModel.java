package com.virtual7.programaticPivotTableBinding.view.managed.ajustable;

import oracle.adf.view.faces.bi.model.PivotTableModel;

public class PivotTableAdjustableModel extends PivotTableModel{
  public PivotTableAdjustableModel() {
      m_ds = new FakeDataSource();
      setDataSource(m_ds);
  }
  
  FakeDataSource m_ds = null;
}
