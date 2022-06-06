package history;

public class InvalidMegaNumberException extends RuntimeException {
    public InvalidMegaNumberException(int number) {
        super(String.format("invalid mega number: %s", number));
    }
}
