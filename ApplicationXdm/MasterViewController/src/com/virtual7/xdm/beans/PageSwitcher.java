package com.virtual7.xdm.beans;

import com.virtual7.xdm.beans.UiState;

import oracle.adf.controller.TaskFlowId;

public class PageSwitcher {
    private UiState currentUiState;

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(currentUiState.getCurrentTF());
    }
    
    public void setUiState(UiState state) {
        currentUiState = state;
    }
}
