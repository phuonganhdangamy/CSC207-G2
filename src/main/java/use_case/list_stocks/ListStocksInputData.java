package use_case.list_stocks;

/**
 * Represents the input data required to list the stocks owned by a user.
 */
public class ListStocksInputData {

    private final String username;

    /**
     * Constructs a new ListStocksInputData object.
     *
     * @param username the username of the user; must not be null
     * @throws IllegalArgumentException if username is null
     */
    public ListStocksInputData(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null.");
        }
        this.username = username;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }
}

