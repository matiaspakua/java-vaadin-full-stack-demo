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
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import es.manolo.demo_jug.data.Contact;
import es.manolo.demo_jug.data.ContactRepository;
import org.springframework.data.domain.PageRequest;

@Route
public class ContactsView extends VerticalLayout {
    private Grid<Contact> grid = new Grid<>(Contact.class);
    private final TextField name = new TextField("Name");
    private final TextField surname = new TextField("Surname");
    private final TextField phone = new TextField("Phone");
    private final TextField email = new TextField("Email");
    private final DatePicker birthday = new DatePicker("Birthday");
    private final TextField address = new TextField("Address");
    private Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);

    public ContactsView(ContactRepository repo) {
        grid.setColumns("name", "surname", "phone", "email", "birthday", "address");
        grid.setItems(repo.findAll());

        grid.setItems(query -> repo.findAll(
                        PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        binder.bindInstanceFields(this);
        add(grid);
        FormLayout formLayout = new FormLayout();
        formLayout.add(name, surname, phone, email, birthday, address);
        formLayout.setVisible(false);
        add(formLayout);
        grid.asSingleSelect().addValueChangeListener(e -> binder.setBean(e.getValue()));

        // necesito botones para guardar, borrar y nuevo, cancelar
        Button save = new Button("Save", e -> {
            if (binder.validate().isOk()) {
                Contact contact = binder.getBean();
                repo.save(contact);
                grid.setItems(repo.findAll());
            }
        });
        Button delete = new Button("Delete", e -> {
            Contact contact = binder.getBean();
            repo.delete(contact);
            grid.setItems(repo.findAll());
        });
        Button cancel = new Button("Cancel", e -> {
            binder.setBean(null);
        });
        Button add = new Button("Add", e -> {
            binder.setBean(new Contact());
        });

        HorizontalLayout actions = new HorizontalLayout(save, delete, cancel);
        formLayout.add(actions);
        this.add(add);

        binder.addStatusChangeListener(e -> {
           formLayout.setVisible(binder.getBean() != null);
           add.setVisible(binder.getBean() == null);
        });
    }

}
