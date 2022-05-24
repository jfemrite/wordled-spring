package com.wordled.views.components;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.wordled.security.SecurityService;
import com.wordled.security.SecurityUtils;
import org.apache.catalina.security.SecurityUtil;

public class CustomMenuBar extends MenuBar{

    private MenuBar menuBar;

    public CustomMenuBar(SecurityService securityService, boolean playerLoggedIn) {

        // Instance
        // Security service to logout user if they are logged in

        menuBar = new MenuBar();
         menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON);

         MenuItem play = menuBar.addItem(new Icon(VaadinIcon.PLAY));
         play.add(new Text("Play"));
         // Navigate to page on click
         play.addClickListener(e -> {
             UI.getCurrent().navigate("");
         });

         MenuItem leaderboard = menuBar.addItem(new Icon(VaadinIcon.TROPHY));
         leaderboard.add(new Text("Leaderboard"));
         // Navigate to page on click
         leaderboard.addClickListener(e -> {
            UI.getCurrent().navigate("leaderboard");
         });

        MenuItem userButton = menuBar.addItem(new Icon(VaadinIcon.USER));
         if(playerLoggedIn) {
             userButton.add(new Text("Log out"));
         } else {
             userButton.add(new Text("Log in"));
         }
         // Navigate to page on click
        userButton.addClickListener(e -> {
            if(playerLoggedIn) {
                securityService.logout();
            } else {
                UI.getCurrent().navigate("login");
            }

         });

    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

}
