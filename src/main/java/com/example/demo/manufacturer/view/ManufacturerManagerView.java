package com.example.demo.manufacturer.view;

import com.example.demo.core.component.MenuComponent;
import com.example.demo.manufacturer.entity.Manufacturer;
import com.example.demo.manufacturer.service.ManufacturerService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

//http://localhost:8081/manufacturermanager
@Route
public class ManufacturerManagerView extends VerticalLayout {

    private Manufacturer selectedManufacturer;
    private VerticalLayout form;
    private TextField name;
    private Binder<Manufacturer> binder;

    @Autowired
    private ManufacturerService service;

    @PostConstruct
    public void init(){
        add(new MenuComponent());
        Grid<Manufacturer> grid = new Grid<>();
        grid.setItems(service.findAll());
        grid.addColumn(Manufacturer::getId).setHeader("ID");
        grid.addColumn(Manufacturer::getName).setHeader("Manufacturer name");
        addButtonBar(grid);
        add(grid);
        addForm(grid);
    }

    private void addButtonBar(Grid<Manufacturer> grid) {
        HorizontalLayout buttonBar = new HorizontalLayout();
        Button deleteBtn = new Button();
        deleteBtn.setEnabled(false);
        deleteBtn.setText("Delete");
        deleteBtn.setIcon(VaadinIcon.TRASH.create());
        deleteBtn.addClickListener(buttonClickEvent -> {
            service.deleteById(selectedManufacturer.getId());
            grid.setItems(service.findAll());
            selectedManufacturer = null;
            deleteBtn.setEnabled(false);
            form.setVisible(false);
            Notification.show("Deleted");
        });

        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedManufacturer = event.getValue();
            deleteBtn.setEnabled(selectedManufacturer != null);
            form.setVisible(selectedManufacturer != null);
            binder.setBean(selectedManufacturer);
        });
        Button addBtn = new Button();
        addBtn.setText("Add");
        addBtn.addClickListener(buttonClickEvent -> {
            selectedManufacturer = new Manufacturer();
            binder.setBean(selectedManufacturer);
            form.setVisible(true);
        });
        addBtn.setIcon(VaadinIcon.PLUS.create());
        Select<String> selectSort = new Select<>();
        selectSort.setLabel("Sort option");
        selectSort.setItems("ID","NAME");
        Button sortButton = new Button();
        sortButton.setText("Sort data");
        sortButton.addClickListener(buttonClickEvent -> {
            if (selectSort.getValue().equals("ID")){
                grid.setItems(service.sortAllById());
                selectSort.setValue("");
            }
            if (selectSort.getValue().equals("NAME")){
                grid.setItems(service.sortAll());
                selectSort.setValue("");
            }

        });
        TextField nameField = new TextField();
        Button idButton = new Button();
        idButton.setText("Search by id");
        idButton.setEnabled(true);
        idButton.addClickListener(buttonClickEvent -> {
            String nameValue = nameField.getValue();
            if (nameValue!=null){
                if (service.findById(Long.parseLong(nameValue))!=null){
                    grid.setItems(service.findById(Long.parseLong(nameValue)));
                }else{
                    Notification.show("Cannot find Manufacturer with this id"+nameValue);
                }

                nameField.setValue("");
            }
        });

        Button listButton = new Button();
        listButton.setText("List all manufacturers");
        listButton.setEnabled(true);
        listButton.addClickListener(event->{
            grid.setItems(service.findAll());
        });
        buttonBar.add(deleteBtn, addBtn,selectSort,sortButton,nameField,idButton,listButton);
        add(buttonBar);
    }

    private void addForm(Grid<Manufacturer> grid) {
        form = new VerticalLayout();
        binder = new Binder<>(Manufacturer.class);
        name = new TextField();
        form.add(new Text("Manufacturer Name"), name);
        Button saveBtn = new Button();
        saveBtn.setText("Save");
        saveBtn.addClickListener(buttonClickEvent -> {
            if (selectedManufacturer.getId() != null) {
                service.update(selectedManufacturer);
            } else {
                service.create(selectedManufacturer);
            }
            grid.setItems(service.findAll());
            form.setVisible(false);
        });
        form.add(saveBtn);
        add(form);
        form.setVisible(false);
        binder.bindInstanceFields(this);
    }

}
