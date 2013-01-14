package com.eurofighter.fileupload.avscan;

import java.io.IOException;

public class AVServiceException extends Exception {

	public AVServiceException(IOException e) {
		super(e);
	}

}
