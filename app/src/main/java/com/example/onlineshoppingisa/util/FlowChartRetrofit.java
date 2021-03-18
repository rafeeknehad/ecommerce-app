package com.example.onlineshoppingisa.util;

public class FlowChartRetrofit {
    private String years;
    private String month;
    private String numberOfOrders;

    public FlowChartRetrofit(String years, String month, String numberOfOrders) {
        this.years = years;
        this.month = month;
        this.numberOfOrders = numberOfOrders;
    }

    public FlowChartRetrofit() {
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(String numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }
}
