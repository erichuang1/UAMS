public class Result<T> {
    public T value;
    public ErrorCode error;

    Result(T value, ErrorCode error) {
        this.value = value;
        this.error = error;
    }

    public boolean containsError() {
        return error != null;
    }
}
