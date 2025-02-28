package org.example.exception;

import java.nio.file.Path;
import java.util.function.Supplier;

/**
 * 파일이 존재하지 않을 때 던지는 예외
 */
public class CustomFileNotExistsException extends RuntimeException {

    public CustomFileNotExistsException(Path targetFilePath) {
        super("File not exists. path=["  +  targetFilePath + "]" + " absolutePath=[" + targetFilePath.toAbsolutePath() + "]");
    }

    public CustomFileNotExistsException(Path targetFilePath, Throwable cause) {
        super("File not exists. path=["  +  targetFilePath + "]" + " absolutePath=[" + targetFilePath.toAbsolutePath() + "]", cause);
    }

    public CustomFileNotExistsException(Path targetFilePath, Throwable cause, Supplier<String> additionalMessage) {
        super("File not exists. path=["  +  targetFilePath + "]" + " absolutePath=[" + targetFilePath.toAbsolutePath() + "]" + " additionalMessage=[" + additionalMessage.get() + "]", cause);
    }

}
