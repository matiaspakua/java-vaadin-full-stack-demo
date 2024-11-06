package es.manolo.demo_jug.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    MainLayout() {
        addToNavbar(new HorizontalLayout(new DrawerToggle(), new H3("Vaadin Demo Jug")));
        addToDrawer(new VerticalLayout(new RouterLink("Contacts", MasterDetail.class), new RouterLink("AI Chat", AiChatView.class)));
    }
}
