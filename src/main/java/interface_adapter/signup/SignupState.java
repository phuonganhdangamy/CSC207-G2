package interface_adapter.signup;

/**
 * The state for the Signup View Model.
 */
public class SignupState {
    private String username = "";
    private String error;
    private String password = "";

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }
    public String getError() {
        return error;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public String toString() {
        return "SignupState{"
                + "username='" + username + '\''
                + ", password='" + password + '\''
                + '}';
    }
}
