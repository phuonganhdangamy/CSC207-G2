package interface_adapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Model for the View Manager. Its state is the name of the View which
 * is currently active. An initial state of "" is used.
 */
public class ViewManagerModel extends ViewModel<String> {

    private final Map<String, ViewModel<?>> viewModels = new HashMap<>();
    private ViewModel<?> activeViewModel;

    public ViewManagerModel() {
        super("view manager");
        this.setState("");
    }

    /**
     * Adds a ViewModel to the manager.
     *
     * @param name      the name of the ViewModel
     * @param viewModel the ViewModel instance
     */
    public void addViewModel(String name, ViewModel<?> viewModel) {
        viewModels.put(name, viewModel);
    }

    /**
     * Switches the active ViewModel by name.
     *
     * @param name the name of the ViewModel to activate
     */
    public void switchTo(String name) {
        if (viewModels.containsKey(name)) {
            activeViewModel = viewModels.get(name);
            System.out.println("Switched to ViewModel: " + name);
        }
        else {
            System.out.println("ViewModel not found: " + name);
        }
    }

    /**
     * Gets the currently active ViewModel.
     *
     * @return the active ViewModel
     */
    public ViewModel getActiveViewModel() {
        return activeViewModel;
    }
}
