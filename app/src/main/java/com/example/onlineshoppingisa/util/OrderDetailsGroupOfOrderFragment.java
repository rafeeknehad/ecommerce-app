package com.example.onlineshoppingisa.util;

import java.util.Date;
import java.util.List;

public class OrderDetailsGroupOfOrderFragment {

    private java.util.Date Date;
    private List<OrderDetailsOfOrderFragment> list;

    public OrderDetailsGroupOfOrderFragment(Date date, List<OrderDetailsOfOrderFragment> list) {
        Date = date;
        this.list = list;
    }

    public Date getDate() {
        return Date;
    }

    public List<OrderDetailsOfOrderFragment> getList() {
        return list;
    }
}
