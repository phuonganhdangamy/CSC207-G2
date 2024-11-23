package use_case.signup;

import data_access.InMemoryUserDataAccessObject;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SignupInteractorTest {
    @Test
    void successTest() {
        SignupInputData inputData = new SignupInputData("CSC207", "group2");
        SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                // 2 things to check: the output data is correct, and the user has been created in the DAO.
                assertEquals("CSC207", outputData.getUsername());
                assertTrue(userRepository.existsByName("CSC207"));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToLoginView() {
                // This is expected
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, successPresenter, new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureUserExistsTest() {
        SignupInputData inputData = new SignupInputData("Paul", "password");
        SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // Add Paul to the repo so that when we check later they already exist
        UserFactory factory = new UserFactory();
        User user = factory.create("Paul", "pwd");
        userRepository.save(user);

        // This creates a presenter that tests whether the test case is as we expect.
        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("User already exists.", error);
            }

            @Override
            public void switchToLoginView() {
                // This is expected
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter, new UserFactory());
        interactor.execute(inputData);
    }
}

