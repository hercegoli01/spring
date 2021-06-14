package com.example.demo.security.view;

import com.example.demo.core.component.MenuComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AccessDeniedView extends VerticalLayout {
    public AccessDeniedView(){
        add(new MenuComponent());
        add("Denied, not enought permissons...");
    }
}
