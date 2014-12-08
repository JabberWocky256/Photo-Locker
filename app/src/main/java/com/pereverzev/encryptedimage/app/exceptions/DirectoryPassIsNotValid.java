package com.pereverzev.encryptedimage.app.exceptions;

import java.io.IOException;

/**
 * Created by Александр on 26.11.2014.
 */
public class DirectoryPassIsNotValid extends IOException {
    /**
     * Constructs a new {@code EmptyDirectoryException} with its stack trace
     * filled in.
     */
    public DirectoryPassIsNotValid() {
    }

    /**
     * Constructs a new {@code EmptyDirectoryException} with its stack trace and
     * detail message filled in.
     *
     * @param detailMessage
     *            the detail message for this exception.
     */
    public DirectoryPassIsNotValid(String detailMessage) {
        super(detailMessage);
    }
}
