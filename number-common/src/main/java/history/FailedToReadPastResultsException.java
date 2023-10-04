package history;

public class FailedToReadPastResultsException extends RuntimeException {
    public FailedToReadPastResultsException(String fileName) {
        super(String.format("Failed to read past results from: %s", fileName));
    }
}
