package use_case.logout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import data_access.DBUserDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.StockFactory;
import entity.User;
import entity.UserFactory;

class LogoutInteractorTest {

    @Test
    void successTest() {
        final LogoutInputData inputData = new LogoutInputData("Mindyissleepy");
        final DBUserDataAccessObject userRepository = new DBUserDataAccessObject(new StockFactory(), new UserFactory());

        // Paul has been already added to
        userRepository.setCurrentUsername("Mindyissleepy");

        // creates a successPresenter that tests whether the test case is as we expect.
        final LogoutOutputBoundary successPresenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData user) {
                // check that the output data contains the username of who logged out
                assertEquals("Mindyissleepy", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                Assertions.fail("Use case failure is unexpected.");
            }
        };

        final LogoutInputBoundary interactor = new LogoutInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
        // check that the user was logged out
        assertNull(userRepository.getCurrentUsername());
    }

    @Test
    void successTestInMemory() {
        final LogoutInputData inputData = new LogoutInputData("TestInmemory");
        final InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        // Add user to the repository and set as current user
        final UserFactory factory = new UserFactory();
        final User user = factory.create("TestInmemory", "123456");
        userRepository.save(user);
        userRepository.setCurrentUsername("TestInmemory");

        // Debugging: Check initial state
        System.out.println("Before logout, current user: " + userRepository.getCurrentUsername());

        final LogoutOutputBoundary successPresenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData user) {
                System.out.println("SuccessView called with user: " + user.getUsername());
                assertEquals("TestInmemory", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                System.out.println("FailView called with error: " + error);
                Assertions.fail("Use case failure is unexpected.");
            }
        };

        final LogoutInputBoundary interactor = new LogoutInteractor(userRepository, successPresenter);
        interactor.execute(inputData);

        // Debugging: Check final state
        System.out.println("After logout, current user: " + userRepository.getCurrentUsername());
        assertNull(userRepository.getCurrentUsername());
    }

    @Test
    void failTest() {
        final LogoutInputData inputData = new LogoutInputData("InvalidUser");
        final InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        // Mock presenter to handle failure
        final LogoutOutputBoundary failPresenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData user) {
                Assertions.fail("Logout should have failed but was successful.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Logout failed, please try again", error);
            }
        };

        // Create interactor with a repository that does not have the user
        final LogoutInputBoundary interactor = new LogoutInteractor(userRepository, failPresenter);

        // Execute the logout operation
        interactor.execute(inputData);

        // Assert that the user is still null (no current user)
        assertNull(userRepository.getCurrentUsername());
    }

    @Test
    void logoutInputDataTest() {
        final LogoutInputData inputData = new LogoutInputData("TestUser");

        // Validate that the username is set correctly
        assertEquals("TestUser", inputData.getUsername());
    }

    @Test
    void logoutOutputDataTest() {
        final LogoutOutputData outputData = new LogoutOutputData(true, "Logout successful", "TestUser");

        // Validate the fields
        assertTrue(outputData.isSuccess());
        assertEquals("Logout successful", outputData.getMessage());
        assertEquals("TestUser", outputData.getUsername());
    }

}
