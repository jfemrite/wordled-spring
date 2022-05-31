package com.wordled.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;
import com.wordled.Application;
import com.wordled.Player;
import com.wordled.PlayerData;
import com.wordled.security.RegistrationHandler;
import com.wordled.security.SecurityConfig;
import com.wordled.security.SecurityService;
import com.wordled.security.SecurityUtils;
import com.wordled.views.components.CustomMenuBar;
import org.apache.catalina.security.SecurityUtil;

// Routes page to .../login
@Route("login")
@PageTitle("Login | !Wordled")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    // Login form to login ui
    private final LoginForm login = new LoginForm();

    // Button used to register an account
    private Button registerAccountButton;

    // Text used to click and go to button
    private final Span registerButton;

    // Login view toggle boolean to switch from reigster to login
    private boolean loginViewVisible = false;

    // Register and login views
    private VerticalLayout loginComponents;
    private VerticalLayout registerComponents;

    // Register components
    private TextField user = new TextField("Username");
    private PasswordField password = new PasswordField("Password");
    private PasswordField conPassword = new PasswordField("Confirm Password");



    public LoginView(SecurityService securityService) {

        setAlignItems(Alignment.CENTER);

        // Post login form to Spring Security
        login.setAction("login");

        registerButton = new Span("Register");


        add(new CustomMenuBar(securityService, SecurityUtils.isUserLoggedIn()).getMenuBar(), getLoginComponents(), getRegisterComponents(), registerButton);

        registerButton.addClickListener(event -> {
            toggleLoginViews();
        });

        registerAccountButton.addClickListener(event -> {
            try {
                registerAccount(user.getValue(), password.getValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    private VerticalLayout getLoginComponents() {

        if(loginComponents == null) {
            loginComponents = new VerticalLayout();
            loginComponents.setAlignItems(Alignment.CENTER);
            loginComponents.addClassName("login-view");

            loginComponents.add(login);
        }

        return loginComponents;
    }

    private VerticalLayout getRegisterComponents() {

        if(registerComponents == null) {

            registerComponents = new VerticalLayout();

            registerComponents.setAlignItems(FlexComponent.Alignment.CENTER);

            user = new TextField("Username");
            password = new PasswordField("Password");

            user.setRequired(true);
            password.setRequired(true);
            conPassword.setRequired(true);

            registerAccountButton = new Button("Register");

            registerComponents.add(new H1("Sign up"), user, password, conPassword, registerAccountButton);
            registerComponents.setVisible(false);
        }

        return registerComponents;
    }


    private void registerAccount(String user, String pass) throws InterruptedException {

        PlayerData playerData = new PlayerData();

        if(!playerData.doesUserExist(user)) {

            Notification.show("Account created, please login", 2000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            Application.getDatabase().addUser(true, user, pass);


        } else {
            Notification.show("Username already exists", 2000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
        }

    }



    private void toggleLoginViews() {
        loginViewVisible = !loginViewVisible;

        if(loginViewVisible) {
            loginComponents.setVisible(false);
            registerComponents.setVisible(true);
            registerButton.setText("Have an account?");
        } else {
            loginComponents.setVisible(true);
            registerComponents.setVisible(false);
            registerButton.setText("Register Account");
        }

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
