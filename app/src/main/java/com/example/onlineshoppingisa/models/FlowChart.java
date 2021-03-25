package com.example.onlineshoppingisa.models;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class FlowChart implements Comparable<FlowChart> {

    private String orderId;
    @ServerTimestamp
    private Date date;

    private String Id;

    @Exclude
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public FlowChart(String orderId) {
        this.orderId = orderId;
    }

    public FlowChart() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }



    @Override
    public int compareTo(FlowChart o) {
        return getDate().compareTo(o.getDate());
    }
}
