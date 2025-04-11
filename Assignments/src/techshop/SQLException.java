package techshop;

public class SQLException extends Exception {
   
    public SQLException(String message) {
        super(message);
    }

    
    public SQLException(String message, Throwable cause) {
        super(message, cause);
    }

    
    public SQLException(Throwable cause) {
        super(cause);
    }
}
