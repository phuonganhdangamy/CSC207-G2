package use_case.signup;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import data_access.InMemoryUserDataAccessObject;
import entity.User;
import entity.UserFactory;

class SignupInteractorTest {
    @Test
    void successTest() {
        final SignupInputData inputData = new SignupInputData("CSC207", "group2");
        final SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        final SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
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

        final SignupInputBoundary interactor = new SignupInteractor(userRepository, successPresenter,
                new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureUserExistsTest() {
        final SignupInputData inputData = new SignupInputData("Paul", "password");
        final SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // Add Paul to the repo so that when we check later they already exist
        final UserFactory factory = new UserFactory();
        final User user = factory.create("Paul", "pwd");
        userRepository.save(user);

        // This creates a presenter that tests whether the test case is as we expect.
        final SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
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

        final SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter,
                new UserFactory());
        interactor.execute(inputData);
    }
}

