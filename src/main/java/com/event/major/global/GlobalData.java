package com.event.major.global;

import java.util.ArrayList;
import java.util.List;

import com.event.major.model.Event;

public class GlobalData {
    public static List<Event> cart;
    static{
        cart = new ArrayList<>();
    }
}
