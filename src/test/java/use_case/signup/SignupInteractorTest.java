package use_case.signup;

import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SignupInteractorTest {
    private TestUserDataAccessObject userRepository;
    private TestSignupOutputBoundary presenter;
    private UserFactory userFactory;
    private SignupInteractor signupInteractor;

    @BeforeEach
    void setUp() {
        userRepository = new TestUserDataAccessObject();
        presenter = new TestSignupOutputBoundary();
        userFactory = new UserFactory();
        signupInteractor = new SignupInteractor(userRepository, presenter, userFactory);
    }

    @Test
    void SuccessTest() {
        SignupInputData inputData = new SignupInputData("Paul", "password");

        signupInteractor.execute(inputData);

        assertTrue(userRepository.userSaved); // Check that the user was saved
        assertFalse(presenter.useCaseFailed); // Check success view was prepared
        assertEquals("Paul", presenter.outputData.getUsername()); // Check the username in output data
    }

    @Test
    void FailureUserExistsTest() {
        userRepository.existingUsername = "Paul"; // Set an existing username in the test repository
        SignupInputData inputData = new SignupInputData("Paul", "password");

        signupInteractor.execute(inputData);

        assertTrue(presenter.useCaseFailed); // Check that failure view was prepared
        assertEquals("User already exists", presenter.errorMessage); // Check error message work
        assertFalse(userRepository.userSaved);
        // Check save was not called, mean SignupInteractor enforces this rule correctly
    }

    private static class TestUserDataAccessObject implements SignupUserDataAccessInterface {
        boolean userSaved = false;
        String existingUsername = null; // Represent existing user in the database

        @Override
        public boolean existsByName(String username) {
            return username.equals(existingUsername); // Check the username matches the existing user
        }

        @Override
        public void save(User user) {
            if (existsByName(user.getName())) {
                throw new IllegalArgumentException("User already exists");
            }
            userSaved = true; // Mark that the user was saved
        }
    }

    private static class TestSignupOutputBoundary implements SignupOutputBoundary {
        boolean useCaseFailed = false;
        SignupOutputData outputData;
        String errorMessage;

        @Override
        public void prepareSuccessView(SignupOutputData outputData) {
            this.useCaseFailed = false;
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.useCaseFailed = true;
            this.errorMessage = errorMessage;
        }

        @Override
        public void switchToLoginView() {
            // No need anything to perform here
        }
    }
}

