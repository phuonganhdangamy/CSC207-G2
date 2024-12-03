package use_case.signup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
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
                Assertions.fail("Use case failure is unexpected.");
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
                Assertions.fail("Use case success is unexpected.");
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

    @Test
    void failureEmptyFieldsTest() {
        final SignupInputData inputData = new SignupInputData("", "");
        final SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        final SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                Assertions.fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Fields are empty.", error);
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

    @Test
    void switchToLoginViewTest() {
        final SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        final SignupOutputBoundary mockPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                Assertions.fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                Assertions.fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToLoginView() {
                // Ensure this is called
                assertTrue(true);
            }
        };

        final SignupInputBoundary interactor = new SignupInteractor(userRepository, mockPresenter, new UserFactory());
        interactor.switchToLoginView();
    }

    @Test
    void signupInputDataTest() {
        final SignupInputData inputData = new SignupInputData("TestUser", "TestPassword");

        // Validate the fields
        assertEquals("TestUser", inputData.getUsername());
        assertEquals("TestPassword", inputData.getPassword());
    }

    /**
     * Tests the SignupOutputData class explicitly.
     */
    @Test
    void signupOutputDataTest() {
        // Create a SignupOutputData object with a dummy username
        final SignupOutputData outputData = new SignupOutputData("TestUser", false);

        // Validate the fields that exist
        assertEquals("TestUser", outputData.getUsername());
    }

    @Test
    void passwordWithSpacesTest() {
        final SignupInputData inputData = new SignupInputData("UserWithSpaces", "password with spaces");
        final SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        final SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                assertEquals("UserWithSpaces", outputData.getUsername());
                assertTrue(userRepository.existsByName("UserWithSpaces"));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                Assertions.fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToLoginView() {
                // Not needed for this test
            }
        };

        final SignupInputBoundary interactor = new SignupInteractor(userRepository, successPresenter,
                new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void successWithUseCaseFailedFalseTest() {
        final SignupInputData inputData = new SignupInputData("ValidUser", "ValidPassword");
        final SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        final SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                assertEquals("ValidUser", outputData.getUsername());
                assertFalse(outputData.isUseCaseFailed());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                Assertions.fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToLoginView() {
                // Not needed for this test
            }
        };

        final SignupInputBoundary interactor = new SignupInteractor(userRepository, successPresenter,
                new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void switchToLoginViewExplicitCallTest() {
        final SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        final SignupOutputBoundary mockPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                Assertions.fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                Assertions.fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToLoginView() {
                assertTrue(true);
            }
        };

        final SignupInputBoundary interactor = new SignupInteractor(userRepository, mockPresenter, new UserFactory());
        interactor.switchToLoginView();
    }

    @Test
    void failureExistingUserAfterSaveTest() {
        final SignupInputData inputData = new SignupInputData("ExistingUser", "password123");
        final SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // Simulate the user already existing in the repository
        final UserFactory factory = new UserFactory();
        final User user = factory.create("ExistingUser", "pwd");
        userRepository.save(user);

        final SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                Assertions.fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                // Ensure the failure message matches expectations
                assertEquals("User already exists.", errorMessage);
            }

            @Override
            public void switchToLoginView() {
                // Not relevant for this test
            }
        };

        final SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter,
                new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureEmptyUsernameTest() {
        final SignupInputData inputData = new SignupInputData("", "ValidPassword");
        final SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        final SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                Assertions.fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Fields are empty.", errorMessage);
            }

            @Override
            public void switchToLoginView() {
                // Not relevant for this test
            }
        };

        final SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter,
                new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void switchToLoginViewCalledTest() {
        final SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // Add a flag within the anonymous class
        final boolean[] wasCalled = {false};

        final SignupOutputBoundary mockPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                Assertions.fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                Assertions.fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToLoginView() {
                wasCalled[0] = true;
            }
        };

        final SignupInputBoundary interactor = new SignupInteractor(userRepository, mockPresenter, new UserFactory());
        interactor.switchToLoginView();

        // Assert that switchToLoginView was called
        assertTrue(wasCalled[0], "switchToLoginView should have been called.");
    }

    @Test
    void failureEmptyPasswordOnlyTest() {
        final SignupInputData inputData = new SignupInputData("ValidUser", "");
        final SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        final SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                Assertions.fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Fields are empty.", errorMessage);
            }

            @Override
            public void switchToLoginView() {
                // Not needed for this test
            }
        };

        final SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter,
                new UserFactory());
        interactor.execute(inputData);
    }

}

