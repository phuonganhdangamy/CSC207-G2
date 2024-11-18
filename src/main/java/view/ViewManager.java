package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewManager implements PropertyChangeListener {
    private ViewManagerModel viewManagerModel;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public ViewManager(JPanel cardPanel, CardLayout cardLayout, ViewManagerModel viewManagerModel) {
        // cardPanel has all the views
        this.cardPanel = cardPanel;
        // ViewManagerModel keeps track of which view is shown
        this.viewManagerModel = viewManagerModel;

        // Will allow us to change the view
        this.cardLayout = cardLayout;

        //ViewManagerModel has the name of the view that needs to be shown
        //If the view changes (due to a use case), this class is alerted
        this.viewManagerModel.addPropertyChangeListener(this);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //Allows us to change the view

        //If the property that has changed is the state, determine what view should be
        // shown to the user
        if (evt.getPropertyName().equals("state")) {
            final String viewModelName = (String) evt.getNewValue();
            cardLayout.show(cardPanel, viewModelName);
        }


    }
}
