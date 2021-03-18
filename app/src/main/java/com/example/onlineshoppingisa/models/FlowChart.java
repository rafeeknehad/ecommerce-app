package com.example.onlineshoppingisa.models;

public class FlowChart {

    private String orderId;

    public FlowChart(String orderId) {
        this.orderId = orderId;
    }

    public FlowChart() {
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
