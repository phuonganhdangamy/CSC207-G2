package view;

import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;

public class LoggedInView extends JPanel{
    private final String viewName = "logged in";

    public LoggedInView(LoggedInViewModel loggedInViewModel) {
    }

    public String getViewName() {
        return viewName;
    }



    public static void main(String[] args){
        //Change for this class to see UI
        JPanel signUpView = new SignupView(new SignupViewModel());
        JFrame frame = new JFrame();
        frame.add(signUpView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
