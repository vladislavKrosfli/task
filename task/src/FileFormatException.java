public class FileFormatException extends Throwable {
    public FileFormatException(String wrong_filename) {
        super(wrong_filename);
    }
}
