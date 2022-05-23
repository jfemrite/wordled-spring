package com.wordled.views;

import com.vaadin.flow.component.textfield.Autocapitalize;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;

import java.util.ArrayList;

public class StyleHandler {

    /**
     * Enables a text field for client input and sets input to that line
     * @param lines List of lines from MainView.java
     * @param enabledLine Line you want to enable client input for
     * @throws NullPointerException In-case you point to a null line
     */
    public static void enableField(ArrayList<TextField> lines, int enabledLine) throws NullPointerException {
        for(int i = 0; i < lines.size(); i++) {
            if(i == enabledLine) {
                lines.get(i).setAutocapitalize(Autocapitalize.CHARACTERS);
                lines.get(i).setAutoselect(true);
                lines.get(i).setAutofocus(true);
                lines.get(i).setRequired(true);
                lines.get(i).setEnabled(true);
                lines.get(i).addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
                lines.get(i).setClassName("customLetterSpacing");
                lines.get(i).setMaxLength(5);
            } else {
                lines.get(i).setEnabled(false);
                lines.get(i).setClassName("customLetterSpacing");
            }
            lines.get(i).getStyle().set("font-weight", "800")
                    .set("font-family", "monospace")
                    .set("font-size", "35px")
                    .set("width", "250px")
                    .set("height", "45px")
                    .set("letter-spacing", "30px");
        }
    }


}
