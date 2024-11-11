package view;

import interface_adapter.signup.SignupViewModel;

import javax.swing.*;

public class LoggedInView {



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
