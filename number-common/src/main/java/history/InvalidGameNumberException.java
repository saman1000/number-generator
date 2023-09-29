package history;

public class InvalidGameNumberException extends RuntimeException {
    public InvalidGameNumberException(int number) {
        super(String.format("invalid mega number: %s", number));
    }
}
