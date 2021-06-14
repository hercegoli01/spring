package com.example.demo.core.component;

import com.example.demo.security.config.SecurityUtils;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MenuComponent extends HorizontalLayout {

    public MenuComponent() {

        Anchor manufacturerLink = new Anchor();
        manufacturerLink.setHref("/manufacturermanager");
        manufacturerLink.setText("Manufacturers");

        Anchor vehicleLink = new Anchor();
        vehicleLink.setHref("/vehiclemanager");
        vehicleLink.setText("Vehicles");

        add(manufacturerLink, vehicleLink);

        if (SecurityUtils.isAdmin()) {
            Anchor userLink = new Anchor();
            userLink.setHref("/usermanager");
            userLink.setText("Users");
            add(userLink);
        }


    }
}
