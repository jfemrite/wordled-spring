package com.wordled.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.wordled.security.SecurityService;
import com.wordled.security.SecurityUtils;
import com.wordled.views.components.CustomMenuBar;
import org.apache.catalina.security.SecurityUtil;

import java.util.ArrayList;

@PageTitle("!Wordled")
@Route(value = "")
public class MainView extends VerticalLayout {

            /* Component fields */

    // Where results are stored for the player
    private ArrayList<HorizontalLayout> resultLayouts;

    // Text fields for input for player
    private ArrayList<TextField> lines;

    private Button guess;

    public MainView(SecurityService securityService) {

        // Generate invisible characters used to show results
        generateResultLayouts();

        // Generate user inputs for wordle
        generateWordleInputs();

        // FIELDS
        guess = new Button("Guess");

        // STYLE
        setMargin(true);

        StyleHandler.enableField(lines, 0);

        Span playerWelcome = new Span();
        if(SecurityUtils.isUserLoggedIn()) {
            playerWelcome.setText("Welcome back, " + securityService.getAuthenticatedUser().getUsername());
        }

        // ALIGNMENTS
        VerticalLayout vLayout = new VerticalLayout();
        vLayout.add(new CustomMenuBar(securityService, SecurityUtils.isUserLoggedIn()).getMenuBar());
        vLayout.add(new H1("!Wordled"));
        vLayout.add(resultLayouts.get(0));
        vLayout.add(lines.get(0));
        vLayout.add(resultLayouts.get(1));
        vLayout.add(lines.get(1));
        vLayout.add(resultLayouts.get(2));
        vLayout.add(lines.get(2));
        vLayout.add(resultLayouts.get(3));
        vLayout.add(lines.get(3));
        vLayout.add(resultLayouts.get(4));
        vLayout.add(lines.get(4));
        vLayout.add(resultLayouts.get(5));
        vLayout.add(lines.get(5));
        vLayout.add(guess);
        vLayout.setAlignItems(Alignment.CENTER);

        setHorizontalComponentAlignment(Alignment.CENTER, vLayout);

        add(vLayout);

        // EVENTS
        guess.addClickListener(e -> {
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).isEnabled()) {

                    if(lines.get(i).getValue().length() != 5) {
                        // TODO: Animations?
                        //Notification.show(lines.get(i).getValue() + " is not long enough", 1500, Notification.Position.TOP_CENTER);
                    } else {

                        StringBuilder input = new StringBuilder(lines.get(i).getValue());

                        // TODO: Implentation to send input to wordle class
                        Notification.show(lines.get(i).getValue() + " was submitted", 1500, Notification.Position.TOP_CENTER);

                        for(int row = 0; row < resultLayouts.get(i).getChildren().count(); row++) {
                            setResult(i, row, 0, input.charAt(row));
                        }

                        if(i == 5) {
                            Notification.show("Thanks for playing!", 5000, Notification.Position.BOTTOM_CENTER);
                        }

                    }

                }
            }
        });
    }

    private void generateResultLayouts() {
        //GENERATES INVISIBLE RESULT CHARACTERS USED FOR RESULTS
        resultLayouts = new ArrayList<>();
        for(int line = 0; line < 6; line++) {

            HorizontalLayout layout = new HorizontalLayout();

            for(int row = 0; row < 5; row++) {
                Span elem = new Span();
                elem.getStyle().set("margin-top", "0");
                layout.addComponentAtIndex(row, elem);
                layout.setVisible(false);
                layout.setSpacing(false);
            }

            resultLayouts.add(line, layout);
        }
    }

    private void generateWordleInputs() {
        lines = new ArrayList<>();
        lines.add(new TextField(""));
        lines.add(new TextField(""));
        lines.add(new TextField(""));
        lines.add(new TextField(""));
        lines.add(new TextField(""));
        lines.add(new TextField(""));
    }

    //TODO: Component getters and setters
    public ArrayList<TextField> getLines() {
        return lines;
    }

    //TODO: Possible move this to StyleHandler.java??
    /**
     * Sets a specific client character status
     * @param line Line you want to change
     * @param col The specific character in the line you want to change
     * @param status 0 = red, 1 = yellow, 2 = green;
     * @param input Client's character input
     */
    public void setResult(int line, int col, int status, char input) {

        //TODO: Find better solution to setting lines visible here
        lines.get(line).setVisible(false);

        resultLayouts.get(line).setVisible(true);

        for(int i = 0; i < 5; i++) {

            Span currSpan = (Span) resultLayouts.get(line).getChildren().toArray()[col];

            currSpan.setText(Character.toString(input).toUpperCase());

            switch (status) {
                case 0:
                    currSpan.getStyle().set("color", "red");
                case 1:
                    currSpan.getStyle().set("color", "yellow");
                case 2:
                    currSpan.getStyle().set("color", "green");
            }

            currSpan.getStyle().set("font-weight", "1000")
                    .set("font-family", "monospace")
                    .set("font-size", "35px");

        }

        StyleHandler.enableField(lines, line + 1);

    }
}
