package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import interface_adapter.ViewManagerModel;

/**
 * ViewManager handles transitions between views and manages interactions with ViewModels.
 */
public class ViewManager {
    private final ViewManagerModel viewModelManager;

    public ViewManager(ViewManagerModel viewModelManager) {
        this.viewModelManager = viewModelManager;
    }

    /**
     * Switches the active view to the specified view name.
     *
     * @param viewName The name of the view to switch to.
     */
    public void switchTo(String viewName) {
        try {
            viewModelManager.switchTo(viewName);
            System.out.println("Active View: " + viewModelManager.getActiveViewModel().getViewName());
        } catch (IllegalArgumentException e) {
            System.err.println("ViewManager Error: " + e.getMessage());
        }
    }

    /**
     * Gets the currently active ViewModel.
     *
     * @return The active ViewModel, or null if none is active.
     */
    public ViewModel<?> getCurrentViewModel() {
        return viewModelManager.getActiveViewModel();
    }

    /**
     * Updates the state of the currently active ViewModel.
     *
     * @param newState The new state to set for the active ViewModel.
     * @param <T>      The type of the state object.
     */
    public <T> void updateCurrentViewModelState(T newState) {
        ViewModel<?> activeViewModel = viewModelManager.getActiveViewModel();
        if (activeViewModel != null) {
            try {
                @SuppressWarnings("unchecked")
                ViewModel<T> typedViewModel = (ViewModel<T>) activeViewModel;
                typedViewModel.setState(newState);
                System.out.println("Updated state for ViewModel: " + typedViewModel.getViewName());
            } catch (ClassCastException e) {
                System.err.println("State update failed: Invalid state type for the active ViewModel.");
            }
        } else {
            System.err.println("No active ViewModel to update.");
        }
    }
}
