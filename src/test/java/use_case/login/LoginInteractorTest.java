package use_case.login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import data_access.DBUserDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.StockFactory;
import entity.User;
import entity.UserFactory;
import use_case.list_stocks.ListStocksInputBoundary;
import use_case.list_stocks.ListStocksInputData;
import use_case.profit_loss.ProfitLossInputBoundary;

class LoginInteractorTest {

    @Test
    void successTest() {
        final LoginInputData inputData = new LoginInputData("anhdang", "anhdang");
        final LoginUserDataAccessInterface userRepository = new DBUserDataAccessObject(new StockFactory(),
                new UserFactory());

        // This creates a successPresenter that tests whether the test case is as we expect.
        final LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("anhdang", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToSignUpView() {
                // Intentionally left blank as this is not needed for the test.
            }

        };

        final LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);

        interactor.setViewOwnedStockInteractor(new ListStocksInputBoundary() {
            @Override
            public void execute(ListStocksInputData inputData) {
                // Intentionally left blank as this is not needed for the test.
            }
        });
        interactor.setProfitLossInteractor(new ProfitLossInputBoundary() {
            @Override
            public void execute() {
                // Intentionally left blank as this is not needed for the test.
            }
        });

        interactor.execute(inputData);
    }

    @Test
    void successTestWithInMemory() {
        final LoginInputData inputData = new LoginInputData("Paul", "password");
        final LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // For the success test, we need to add Paul to the data access repository before we log in.
        final UserFactory factory = new UserFactory();
        final User user = factory.create("Paul", "password");
        userRepository.save(user);

        // This creates a successPresenter that tests whether the test case is as we expect.
        final LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("Paul", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToSignUpView() {
                // Intentionally left blank as this is not needed for the test.

            }
        };

        final LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.setViewOwnedStockInteractor(new ListStocksInputBoundary() {
            @Override
            public void execute(ListStocksInputData inputData) {
                // Intentionally left blank as this is not needed for the test.
            }
        });
        interactor.setProfitLossInteractor(new ProfitLossInputBoundary() {
            @Override
            public void execute() {
                // Intentionally left blank as this is not needed for the test.
            }
        });
        interactor.execute(inputData);
    }

    @Test
    void successUserLoggedInTest() {
        final LoginInputData inputData = new LoginInputData("anhdang", "anhdang");
        final LoginUserDataAccessInterface userRepository = new DBUserDataAccessObject(new StockFactory(),
                new UserFactory());

        // This creates a successPresenter that tests whether the test case is as we expect.
        final LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("anhdang", userRepository.getCurrentUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToSignUpView() {
                // Intentionally left blank as this is not needed for the test.

            }
        };

        final LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.setViewOwnedStockInteractor(new ListStocksInputBoundary() {
            @Override
            public void execute(ListStocksInputData inputData) {
                // Intentionally left blank as this is not needed for the test.
            }
        });
        interactor.setProfitLossInteractor(new ProfitLossInputBoundary() {
            @Override
            public void execute() {
                // Intentionally left blank as this is not needed for the test.
            }
        });
        assertNull(userRepository.getCurrentUsername());

        interactor.execute(inputData);
    }

    @Test
    void successUserLoggedInTestInMemory() {
        final LoginInputData inputData = new LoginInputData("Paul", "password");
        final LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // For the success test, we need to add Paul to the data access repository before we log in.
        final UserFactory factory = new UserFactory();
        final User user = factory.create("Paul", "password");
        userRepository.save(user);

        // This creates a successPresenter that tests whether the test case is as we expect.
        final LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("Paul", userRepository.getCurrentUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToSignUpView() {
                // Intentionally left blank as this is not needed for the test.
            }
        };

        final LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.setViewOwnedStockInteractor(new ListStocksInputBoundary() {
            @Override
            public void execute(ListStocksInputData inputData) {
                // Intentionally left blank as this is not needed for the test.
            }
        });
        interactor.setProfitLossInteractor(new ProfitLossInputBoundary() {
            @Override
            public void execute() {
                // Intentionally left blank as this is not needed for the test.
            }
        });
        assertNull(userRepository.getCurrentUsername());

        interactor.execute(inputData);
    }

    @Test
    void failurePasswordMismatchTest() {
        final LoginInputData inputData = new LoginInputData("anhdang", "wrong");
        final LoginUserDataAccessInterface userRepository = new DBUserDataAccessObject(new StockFactory(),
                new UserFactory());

        // This creates a presenter that tests whether the test case is as we expect.
        final LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password for \"anhdang\".", error);
            }

            @Override
            public void switchToSignUpView() {
                // Intentionally left blank as this is not needed for the test.
            }
        };

        final LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failurePasswordMismatchTestInMemory() {
        final LoginInputData inputData = new LoginInputData("Paul", "wrong");
        final LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // For this failure test, we need to add Paul to the data access repository before we log in, and
        // the passwords should not match.
        final UserFactory factory = new UserFactory();
        final User user = factory.create("Paul", "password");
        userRepository.save(user);

        // This creates a presenter that tests whether the test case is as we expect.
        final LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password for \"Paul\".", error);
            }

            @Override
            public void switchToSignUpView() {
                // Intentionally left blank as this is not needed for the test.
            }
        };
        final LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureUserDoesNotExistTest() {
        final LoginInputData inputData = new LoginInputData("Amy", "password");
        final LoginUserDataAccessInterface userRepository = new DBUserDataAccessObject(new StockFactory(),
                new UserFactory());

        // Since Paul was added to the database in the previous tests, try another login data
        // Add Amy to the repo so that when we check later they already exist

        // This creates a presenter that tests whether the test case is as we expect.
        final LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Amy: Account does not exist.", error);
            }

            @Override
            public void switchToSignUpView() {
                // Intentionally left blank as this is not needed for the test.
            }
        };

        final LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }
}
