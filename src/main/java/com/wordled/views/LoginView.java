package com.wordled.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;
import com.wordled.security.SecurityService;
import com.wordled.security.SecurityUtils;
import com.wordled.views.components.CustomMenuBar;
import org.apache.catalina.security.SecurityUtil;

// Routes page to .../login
@Route("login")
@PageTitle("Login | !Wordled")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    private final LoginForm login = new LoginForm();

    public LoginView(SecurityService securityService) {

        addClassName("login-view");
        setAlignItems(Alignment.CENTER);

        // Post login form to Spring Security
        login.setAction("login");


        add(new CustomMenuBar(securityService, SecurityUtils.isUserLoggedIn()).getMenuBar(), new H1("!Wordled"), login);

    }

    // Read queury parameters
    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        // inform user about login auth error
        if(event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }

    }

}
