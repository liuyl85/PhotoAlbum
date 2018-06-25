package com.yun.album.exception;

/**
 * 进行MD5计算时异常
 */
public class MD5DigestException extends Exception {
	private static final long serialVersionUID = 1L;

	public MD5DigestException() {
		super();
	}

	public MD5DigestException(String message, Throwable cause) {
		super(message, cause);
	}

	public MD5DigestException(String message) {
		super(message);
	}

	public MD5DigestException(Throwable cause) {
		super(cause);
	}

}
