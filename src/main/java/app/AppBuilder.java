package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.DBStockDataAccessObject;
import data_access.DBUserDataAccessObject;
import entity.StockFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.LoggedInViewModel;
import interface_adapter.LoggedInState;
import interface_adapter.buy_stock.BuyStockController;
import interface_adapter.buy_stock.BuyStockPresenter;
import interface_adapter.buy_stock.BuyStockViewModel;
import interface_adapter.find_stock.FindStockController;
import interface_adapter.find_stock.FindStockPresenter;
import interface_adapter.find_stock.FindStockViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.profit_loss.ProfitLossController;
import interface_adapter.profit_loss.ProfitLossPresenter;
import interface_adapter.sell_stock.SellStockController;
import interface_adapter.sell_stock.SellStockPresenter;
import interface_adapter.sell_stock.SellStockViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.view_owned_stock.ViewOwnedStockPresenter;
import use_case.buy_stock.BuyStockInputBoundary;
import use_case.buy_stock.BuyStockInteractor;
import use_case.buy_stock.BuyStockOutputBoundary;
import use_case.find_stock.FindStockDataAccessInterface;
import use_case.find_stock.FindStockInputBoundary;
import use_case.find_stock.FindStockInteractor;
import use_case.find_stock.FindStockOutputBoundary;
import use_case.list_stocks.ListStocksInputBoundary;
import use_case.list_stocks.ListStocksInteractor;
import use_case.list_stocks.ListStocksOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;

import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.profit_loss.ProfitLossInputBoundary;
import use_case.profit_loss.ProfitLossInteractor;
import use_case.profit_loss.ProfitLossOutputBoundary;
import use_case.sell_stock.SellStockInputBoundary;
import use_case.sell_stock.SellStockInteractor;
import use_case.sell_stock.SellStockOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final UserFactory userFactory = new UserFactory();
    private final StockFactory stockFactory = new StockFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(stockFactory, userFactory);
    private final DBStockDataAccessObject stockDataAccessObject = new DBStockDataAccessObject();


    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private LoginViewModel loginViewModel;

    private FindStockViewModel findStockViewModel;
    private FindStockView findStockView;

    private BuyStockViewModel buyStockViewModel;
    private SellStockViewModel sellStockViewModel;
    private BuySellStockView buySellStockView;

    // Updated initialization of listStocksOutputBoundary
    private ListStocksOutputBoundary listStocksOutputBoundary;
    private ListStocksInputBoundary listStocksInteractor;

    private ProfitLossOutputBoundary profitLossPresenter;
    private ProfitLossInputBoundary profitLossInteractor;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();

        findStockViewModel = new FindStockViewModel(); // Initialize FindStockViewModel
        findStockView = new FindStockView(findStockViewModel);
        buySellStockView = new BuySellStockView(findStockView);

        loggedInView = new LoggedInView(loggedInViewModel, findStockView, buySellStockView);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        SignupOutputBoundary signUpPresenter = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);

        SignupInputBoundary signUpInteractor = new SignupInteractor(userDataAccessObject, signUpPresenter, userFactory);

        SignupController signupController = new SignupController(signUpInteractor);

        signupView.setSignupController(signupController);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel, signupViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);
        loginInteractor.setProfitLossInteractor(profitLossInteractor);
        loginInteractor.setViewOwnedStockInteractor(listStocksInteractor);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(loginViewModel,
                loggedInViewModel, viewManagerModel);
        final LogoutInputBoundary logoutInteractor = new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);
        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);

        return this;
    }

    /**
     * Adds the FindStock View to the application.
     * @return this builder
     */
    public AppBuilder addFindStockView() {
        findStockViewModel = new FindStockViewModel();
        findStockView = new FindStockView(findStockViewModel);
        cardPanel.add(findStockView, findStockView.getViewName());
        return this;
    }

    /**
     * Adds the FindStock Use Case to the application.
     * @return this builder
     */
    public AppBuilder addFindStockUseCase() {
        final FindStockOutputBoundary findStockOutputBoundary = new FindStockPresenter(findStockViewModel,
                viewManagerModel);
        final FindStockInputBoundary findStockInteractor = new FindStockInteractor(stockDataAccessObject,
                findStockOutputBoundary);

        final FindStockController findStockController = new FindStockController(findStockInteractor);
        findStockView.setFindStockController(findStockController);

        return this;
    }

    /**
     * Adds the SellStock Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSellStockUseCase() {
        final SellStockOutputBoundary sellStockPresenter = new SellStockPresenter(loggedInViewModel, viewManagerModel);

        final FindStockDataAccessInterface stockDatabase = new DBStockDataAccessObject();

        final SellStockInputBoundary sellStockInteractor = new SellStockInteractor(sellStockPresenter,
                userDataAccessObject, stockDatabase);
        sellStockInteractor.setViewOwnedStockInteractor(listStocksInteractor);
        sellStockInteractor.setProfitLossInteractor(profitLossInteractor);
        final SellStockController sellStockController = new SellStockController(sellStockInteractor);

        loggedInView.setSellStockController(sellStockController);
        return this;
    }

    /**
     * Adds the buyStock Use Case to the application.
     * @return this builder
     */
    public AppBuilder addBuyStockUseCase() {
        final BuyStockOutputBoundary buyStockPresenter = new BuyStockPresenter(loggedInViewModel,viewManagerModel);

        //Create new stockDatabase for the sellStockUseCase
        final FindStockDataAccessInterface stockDatabase = new DBStockDataAccessObject();

        final BuyStockInteractor buyStockInteractor = new BuyStockInteractor(buyStockPresenter,
                userDataAccessObject, stockDatabase);
        buyStockInteractor.setViewOwnedStockInteractor(listStocksInteractor);
        buyStockInteractor.setProfitLossInteractor(profitLossInteractor);

        final BuyStockController buyStockController = new BuyStockController(buyStockInteractor);

        loggedInView.setBuyStockController(buyStockController);
        return this;

    }

    /**
     * Adds the ProfitLoss Use Case to the application.
     * @return this builder
     */
    public AppBuilder addProfitLossUseCase() {
        profitLossPresenter = new ProfitLossPresenter(loggedInViewModel, viewManagerModel);
        profitLossInteractor = new ProfitLossInteractor(userDataAccessObject, profitLossPresenter);
        final ProfitLossController profitLossController = new ProfitLossController(profitLossInteractor);

        loggedInView.setProfitLossController(profitLossController);

        return this;
    }
    public AppBuilder addViewStocksUseCase() {
        listStocksOutputBoundary = new ViewOwnedStockPresenter(loggedInViewModel);
        listStocksInteractor = new ListStocksInteractor(listStocksOutputBoundary, userDataAccessObject);

        return this;
    }

    /**
     * Creates the JFrame and the first view is the signup view.
     * Tells the viewManager what view is currently shown to the user.
     * @return application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Stock Market Simulator");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
