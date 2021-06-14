package com.example.demo.user.view;

import com.example.demo.core.component.MenuComponent;
import com.example.demo.user.entity.Role;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.RoleService;
import com.example.demo.user.service.UserService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;


//http://localhost:8081/usermanager
@Route
public class UserManagerView extends VerticalLayout {

    private User selectedUser;
    private VerticalLayout form;
    private TextField userName;
    private CheckboxGroup<Role> authorities;
    private Binder<User> binder;


    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @PostConstruct
    public void init() {
        add(new MenuComponent());
        Grid<User> grid = new Grid<>();
        grid.setItems(userService.findAll());
        grid.addColumn(User::getId).setHeader("ID");
        grid.addColumn(User::getUsername).setHeader("Username");
        grid.addColumn(userEntity -> {
            StringBuilder builder = new StringBuilder();
            userEntity.getAuthorities().forEach(roleEntity -> {
                builder.append(roleEntity.getAuthority()).append(", ");
            });
            return builder.toString();
        }).setHeader("Role");
        addButtonBar(grid);
        add(grid);
        addForm(grid);
    }

    private void addForm(Grid<User> grid) {
        form = new VerticalLayout();
        binder = new Binder<>(User.class);
        userName = new TextField();
        form.add(new Text("Username"), userName);
        authorities = new CheckboxGroup<>();
        authorities.setItems(roleService.findAll());
        authorities.setItemLabelGenerator(Role::getAuthority);
        form.add(new Text("Roles"), authorities);

        Button saveButton = new Button();
        saveButton.setText("Save");
        saveButton.addClickListener(clickEvent -> {
            if (selectedUser.getId() != null) {
                selectedUser.setAuthorities(authorities.getSelectedItems());
                userService.update(selectedUser);
            } else {

                final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

                SecureRandom random = new SecureRandom();
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < 10; i++) {
                    int randomIndex = random.nextInt(chars.length());
                    sb.append(chars.charAt(randomIndex));
                }
                String password = sb.toString();
                Notification passwordNotification = new Notification("Your password is: " + password, 10000);
                passwordNotification.open();
                selectedUser.setPassword(new BCryptPasswordEncoder().encode(password));
                selectedUser.setAuthorities(authorities.getSelectedItems());
                userService.create(selectedUser);

            }
            grid.setItems(userService.findAll());
            form.setVisible(false);
        });

        form.add(saveButton);
        add(form);
        form.setVisible(false);
        binder.bindInstanceFields(this);
    }


    private void addButtonBar(Grid<User> grid) {

        HorizontalLayout buttonBar = new HorizontalLayout();
        Button deleteButton = new Button();
        deleteButton.setEnabled(false);
        deleteButton.setText("Delete");
        deleteButton.setIcon(VaadinIcon.TRASH.create());
        deleteButton.addClickListener(clickEvent -> {
            userService.deleteById(selectedUser.getId());
            grid.setItems(userService.findAll());
            selectedUser = null;
            deleteButton.setEnabled(false);
            form.setVisible(false);
            Notification.show("Successfully deleted user");
        });

        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedUser = event.getValue();
            deleteButton.setEnabled(selectedUser != null);
            form.setVisible(selectedUser != null);
            binder.setBean(selectedUser);
        });

        Button addButton = new Button();
        addButton.setText("Add new user");
        addButton.addClickListener(clickEvent -> {
            selectedUser = new User();
            binder.setBean(selectedUser);
            form.setVisible(true);
        });
        addButton.setIcon(VaadinIcon.PLUS.create());
        Select<String> selectSort = new Select<>();
        selectSort.setLabel("Sort option");
        selectSort.setItems("ID","NAME");
        Button sortButton = new Button();
        sortButton.setText("Sort data");
        sortButton.addClickListener(buttonClickEvent -> {
            if (selectSort.getValue().equals("ID")){
                grid.setItems(userService.sortAllById());
                selectSort.setValue("");
            }
            if (selectSort.getValue().equals("NAME")){
                grid.setItems(userService.sortAll());
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
                if (userService.findById(Long.parseLong(nameValue))!=null){
                    grid.setItems(userService.findById(Long.parseLong(nameValue)));
                }else{
                    Notification.show("Cannot find User with this id"+nameValue);
                }

                nameField.setValue("");
            }
        });
        Button listButton = new Button();
        listButton.setText("List all");
        listButton.setEnabled(true);
        listButton.addClickListener(event->{
           grid.setItems(userService.findAll());
        });
        buttonBar.add(deleteButton, addButton,selectSort,sortButton,nameField,idButton,listButton);
        add(buttonBar);
    }


}
