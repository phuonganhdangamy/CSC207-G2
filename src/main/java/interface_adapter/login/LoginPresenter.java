package interface_adapter.login;

import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {
    @Override
    public void prepareSuccessView(LoginOutputData outputData) {

    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
