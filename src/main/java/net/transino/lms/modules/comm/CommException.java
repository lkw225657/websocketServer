package net.transino.lms.modules.comm;

/**
 * @author veggieg
 * @since 5.0
 */
public class CommException extends RuntimeException {
    private static final long serialVersionUID = 3957230613154795468L;

    public CommException() {
        super();
    }

    public CommException(String message) {
        super(message);
    }

    public CommException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommException(Throwable cause) {
        super(cause);
    }

    protected CommException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
