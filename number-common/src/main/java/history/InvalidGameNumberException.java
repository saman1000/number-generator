package history;

public class InvalidGameNumberException extends RuntimeException {
    public InvalidGameNumberException(int number) {
        super(String.format("invalid number: %s", number));
    }
}
