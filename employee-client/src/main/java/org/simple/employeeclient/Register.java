/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.simple.employeeclient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.View;
import static jakarta.faces.application.StateManager.IS_BUILDING_INITIAL_STATE;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIOutput;
import jakarta.faces.component.html.HtmlBody;
import jakarta.faces.component.html.HtmlCommandButton;
import jakarta.faces.component.html.HtmlForm;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.facelets.Facelet;
import org.primefaces.component.inputtext.InputText;
import java.io.IOException;

/**
 *
 * @author Juneau
 */
@View("/register.xhtml")
@ApplicationScoped
public class Register extends Facelet {

    @Override
    public void apply(FacesContext facesContext, UIComponent root) throws IOException {
        if (!facesContext.getAttributes().containsKey(IS_BUILDING_INITIAL_STATE)) {
            return;
        }

        var components = new ComponentBuilder(facesContext);
        var rootChildren = root.getChildren();

        var docType = new UIOutput();
        docType.setValue("<!DOCTYPE html>");
        rootChildren.add(docType);

        var brTag = new UIOutput();
        brTag.setValue("</br>");

        var headerTag = new UIOutput();
        headerTag.setValue("<h1>Jakarta Faces Form via Java Code</h1>");

        var htmlTag = new UIOutput();
        htmlTag.setValue("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        rootChildren.add(htmlTag);

        HtmlBody body = components.create(HtmlBody.COMPONENT_TYPE);
        rootChildren.add(body);

        body.getChildren().add(headerTag);

        HtmlForm form = components.create(HtmlForm.COMPONENT_TYPE);
        form.setId("form");
        body.getChildren().add(form);

        HtmlOutputText message = components.create(HtmlOutputText.COMPONENT_TYPE);
        message.setId("message");

        InputText inputText = components.create(InputText.COMPONENT_TYPE);
        inputText.setId("nameText");

        HtmlCommandButton actionButton = components.create(HtmlCommandButton.COMPONENT_TYPE);
        actionButton.setId("button");
        actionButton.addActionListener(
                e -> {
                    var outputMessage = "Hello ";
                    if (inputText.getValue() != null && inputText.getValue().toString().length() > 1) {
                        outputMessage += inputText.getValue();
                    } else {
                        outputMessage += "Jakarta EE 10";
                    }
                    message.setValue(outputMessage);
                });
        actionButton.setValue("Submit");
        form.getChildren().add(inputText);
        form.getChildren().add(brTag);
        form.getChildren().add(actionButton);

        rootChildren.add(brTag);

        root.getChildren().add(message);

        htmlTag = new UIOutput();
        htmlTag.setValue("</html>");
        rootChildren.add(htmlTag);

    }

    private static class ComponentBuilder {

        FacesContext facesContext;

        ComponentBuilder(FacesContext facesContext) {
            this.facesContext = facesContext;
        }

        @SuppressWarnings("unchecked")
        <T> T create(String componentType) {
            return (T) facesContext.getApplication().createComponent(facesContext, componentType, null);
        }
    }

}
