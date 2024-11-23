package app;

import javax.swing.JFrame;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     *
     * @param args unused arguments
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        // Set up in the AppBuilder and add the use cases here
        final JFrame application = appBuilder
                .addSignupView()
                .addLoginView()
                .addLoggedInView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addLogoutUseCase()
                .addSellStockUseCase()
                .build();

        // Makes the application visible
        application.pack();
        application.setVisible(true);
    }
}