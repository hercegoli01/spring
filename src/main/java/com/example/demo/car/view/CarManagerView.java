package com.example.demo.car.view;

import com.example.demo.core.component.MenuComponent;
import com.example.demo.manufacturer.entity.Manufacturer;
import com.example.demo.manufacturer.service.ManufacturerService;
import com.example.demo.car.entity.Car;
import com.example.demo.car.service.CarService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

//http://localhost:8081/vehiclemanager
@Route
public class CarManagerView extends VerticalLayout {

    private Car selectedVehicle;
    private VerticalLayout form;
    private TextField name;
    private ComboBox<Manufacturer> manufacturer;
    private NumberField doors;
    private NumberField prodYear;
    private Binder<Car> binder;

    @Autowired
    private CarService service;

    @Autowired
    private ManufacturerService manufacturerService;

    @PostConstruct
    public void init() {


        add(new MenuComponent());
        Grid<Car> grid = new Grid<>();
        grid.setItems(service.findAll());
        grid.addColumn(Car::getId).setHeader("ID");
        grid.addColumn(Car::getName).setHeader("TYPE");
        grid.addColumn(vehicleEntity -> {
            if (vehicleEntity.getManufacturer() != null) {
                return vehicleEntity.getManufacturer().getName();
            }
            return "";
        }).setHeader("Manufacturer");
        grid.addColumn(Car::getIntDoors).setHeader("Doors");
        grid.addColumn(Car::getIntProdYear).setHeader("ProdYear");
        addButtonBar(grid);
        add(grid);
        addForm(grid);


    }


    private void addButtonBar(Grid<Car> grid) {
        HorizontalLayout buttonBar = new HorizontalLayout();
        Button deleteBtn = new Button();
        deleteBtn.setEnabled(false);
        deleteBtn.setText("Delete");
        deleteBtn.setIcon(VaadinIcon.TRASH.create());
        deleteBtn.addClickListener(buttonClickEvent -> {
            service.deleteById(selectedVehicle.getId());
            grid.setItems(service.findAll());
            selectedVehicle = null;
            deleteBtn.setEnabled(false);
            form.setVisible(false);
            Notification.show("Deleted");
        });
        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedVehicle = event.getValue();
            deleteBtn.setEnabled(selectedVehicle != null);
            form.setVisible(selectedVehicle != null);
            binder.setBean(selectedVehicle);
        });
        Button addBtn = new Button();
        addBtn.setText("Add");
        addBtn.addClickListener(buttonClickEvent -> {
            selectedVehicle = new Car();
            binder.setBean(selectedVehicle);
            form.setVisible(true);
        });
        addBtn.setIcon(VaadinIcon.PLUS.create());
        Select<String> selectSort = new Select<>();
        selectSort.setLabel("Sort option");
        selectSort.setItems("ID", "NAME");
        Button sortBtn = new Button();

        sortBtn.setText("Sort");

        sortBtn.addClickListener(buttonClickEvent -> {
            if (selectSort.getValue().equals("ID")){
                grid.setItems(service.sortAllById());
                selectSort.setValue("");
            }
            if(selectSort.getValue().equals("NAME")){
                grid.setItems(service.sortAll());
                selectSort.setValue("");
            }

        });

        TextField nameField = new TextField();
        Button searchButton = new Button();
        searchButton.setText("Search by id");
        searchButton.setEnabled(true);
        searchButton.addClickListener(buttonClickEvent -> {
            String nameValue = nameField.getValue();
            if (nameValue!=null){
                if (service.findById(Long.parseLong(nameValue))!=null){
                    grid.setItems(service.findById(Long.parseLong(nameValue)));
                }else{
                    Notification.show("Cannot find Vehicle with this id"+nameValue);
                }

                nameField.setValue("");
            }
        });
        Button listButton = new Button();
        listButton.setText("List all cars");
        listButton.setEnabled(true);
        listButton.addClickListener(event->{
            grid.setItems(service.findAll());
        });
        buttonBar.add(deleteBtn, addBtn,selectSort,sortBtn,nameField,searchButton,listButton);
        add(buttonBar);
    }


    private void addForm(Grid<Car> grid) {

        form = new VerticalLayout();
        binder = new Binder<>(Car.class);
        name = new TextField();
        form.add(new Text("Vehicle Type"), name);
        manufacturer = new ComboBox<>();
        manufacturer.setItems(manufacturerService.findAll());
        manufacturer.setItemLabelGenerator(Manufacturer::getName);
        form.add(new Text("Manufacturer"), manufacturer);
        doors = new NumberField();

        form.add(new Text("Number of Doors"), doors);
        prodYear = new NumberField();
        form.add(new Text("Prod Year"), prodYear);

        Button saveBtn = new Button();
        saveBtn.setText("Save");
        saveBtn.addClickListener(buttonClickEvent -> {
            if (selectedVehicle.getId() != null) {
                service.update(selectedVehicle);
            } else {
                service.create(selectedVehicle);
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
