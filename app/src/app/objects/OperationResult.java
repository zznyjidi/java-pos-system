package objects;

public class OperationResult<R, E> {
    boolean success;
    String message;
    R result;
    E error;

    private OperationResult(boolean success, String message, R result, E error) {
        this.success = success;
        this.message = message;
        this.result = result;
        this.error = error;
    }

    public static <R, E> OperationResult<R, E> success(R result, String message) {
        return new OperationResult<>(true, message, result, null);
    }

    public static <R, E> OperationResult<R, E> failed(E error, String message) {
        return new OperationResult<>(false, message, null, error);
    }

    public static <R, E> OperationResult<R, E> failed(E error) {
        return new OperationResult<>(false, "", null, error);
    }

    public static <R, E> OperationResult<R, E> success(R result) {
        return new OperationResult<>(true, "", result, null);
    }

    public boolean getStatus() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public <T> T parseResult(SuccessParser<R, T> successParser, ErrorParser<E, T> errorParser) {
        if (success)
            return successParser.parseSuccess(result, message);
        else
            return errorParser.parseError(error, message);
    }

    public interface SuccessParser<R, T> {
        T parseSuccess(R result, String message);
    }

    public interface ErrorParser<E, T> {
        T parseError(E error, String message);
    }

    public void printResult() {
        if (success) {
            if (result instanceof OperationResult result) {
                result.printResult();
                return;
            }
            IO.println(message + result.toString());
        } else {
            if (result instanceof OperationResult result) {
                result.printResult();
                return;
            }
            if (result instanceof Exception exception) {
                exception.printStackTrace();
                return;
            }
            IO.println(message + error.toString());
        }
    }

    public String toString() {
        if (success) {
            return message + result.toString();
        } else {
            return message + error.toString();
        }
    }

    public R getResult() {
        return result;
    }

    public E getError() {
        return error;
    }
}
