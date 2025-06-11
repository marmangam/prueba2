/**
 * 
 */
package us.muit.fs.devicesmanagement.exceptions;

/**
 * 
 */
public class DuplicateDeviceException extends RuntimeException {
    public DuplicateDeviceException(String id) {
        super("A device with Id '" + id + "' already exists.");
    }
}
