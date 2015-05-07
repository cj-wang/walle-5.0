package cn.walle.framework.core.exception;

/**
 * 
 * @author cj
 *
 */
public class SystemException extends RuntimeException {

	public SystemException() {
		super();
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

}
