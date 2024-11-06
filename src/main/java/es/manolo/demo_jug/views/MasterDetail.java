package es.manolo.demo_jug.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import es.manolo.demo_jug.data.Contact;
import es.manolo.demo_jug.data.ContactRepository;



@Route(value = "", layout = MainLayout.class)
public class MasterDetail extends VerticalLayout {

    private Grid<Contact> grid = new Grid<>(Contact.class);

    TextField name = new TextField("Name");
    TextField lastName = new TextField("Last Name");
    TextField email = new TextField("Email");
    TextField phone = new TextField("Phone");
    TextField address = new TextField("Address");
    DatePicker birthDay = new DatePicker("BirthDay");


    MasterDetail(ContactRepository repository) {
        Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);
        binder.bindInstanceFields(this);

        grid.setColumns("name", "lastName", "email", "phone", "address", "birthDay");
        grid.setItems(repository.findAll());
        grid.asSingleSelect().addValueChangeListener(e -> binder.setBean(e.getValue()));

        Button save = new Button("Save", e -> {
            if (binder.validate().isOk()) {
                repository.save(binder.getBean());
                grid.setItems(repository.findAll());
                binder.setBean(null);
            }
        });
        Button cancel = new Button("Cancel", e -> {
            binder.setBean(null);
            grid.asSingleSelect().clear();
        });
        Button delete = new Button("Delete", e -> {
            repository.delete(binder.getBean());
            grid.setItems(repository.findAll());
            binder.setBean(null);
        });
        Button add = new Button("Add", e -> {
            binder.setBean(new Contact());
        });

        HorizontalLayout buttons = new HorizontalLayout(save, cancel, delete);
        FormLayout form = new FormLayout(name, lastName, email, phone, address, birthDay, buttons);
        form.setVisible(false);
        add(grid, add, form);

        binder.addStatusChangeListener(e -> {
            form.setVisible(binder.getBean() != null);
            add.setVisible(binder.getBean() == null);
        });

    }
}
