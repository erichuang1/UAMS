public class Result<T> {
    public T value;
    public ErrorCode errorCode;

    Result(T value, ErrorCode error) {
        this.value = value;
        this.errorCode = error;
    }

    public boolean containsError() {
        return errorCode != null;
    }
}
