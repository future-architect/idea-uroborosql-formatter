package jp.co.future.ideausqlfmt;

/**
 * Provide exceptions that occurred during formatting.
 *
 * @author hoshi
 */
public class UroborosqlFormatterException extends RuntimeException {
	private final String token;

	public UroborosqlFormatterException(String message, String token, String trace, Throwable cause) {
		super(buildMessage(message, token, trace), cause);
		this.token = token;
	}

	private static String buildMessage(String message, String token, String trace) {
		return message + "@token=[" + token + "]\n" + trace;
	}

	public String getToken() {
		return token;
	}

}
