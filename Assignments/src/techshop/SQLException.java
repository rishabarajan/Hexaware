package techshop;

public class SQLException extends Exception {
    // Constructor that accepts a message string
    public SQLException(String message) {
        super(message);
    }

    // Constructor that accepts a message and a cause (another Throwable)
    public SQLException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that accepts a cause (another Throwable)
    public SQLException(Throwable cause) {
        super(cause);
    }
}
