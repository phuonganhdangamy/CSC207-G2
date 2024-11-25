package interface_adapter.logout;

import interface_adapter.LoggedInState;
import interface_adapter.LoggedInViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupViewModel;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;

public class LogoutPresenter implements LogoutOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;


    public LogoutPresenter(LoginViewModel loginViewModel, LoggedInViewModel loggedInViewModel, ViewManagerModel viewManagerModel) {
        this.loginViewModel = loginViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(LogoutOutputData outputData) {

        final LoggedInState loggedInState = new LoggedInState();
        final LoginState loginState = new LoginState();
        loginViewModel.setState(loginState);

        this.viewManagerModel.setState(loginViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();

        loggedInViewModel.setState(loggedInState);


    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
