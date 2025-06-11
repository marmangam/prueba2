/**
 * 
 */
package us.muit.fs.devicesmanagement.exceptions;

/**
 * 
 */
public class DuplicateMacAddressException extends RuntimeException {
    public DuplicateMacAddressException(String mac) {
        super("A device with MAC address '" + mac + "' already exists.");
    }
}

