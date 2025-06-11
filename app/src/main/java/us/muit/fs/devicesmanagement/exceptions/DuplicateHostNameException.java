/**
 * 
 */
package us.muit.fs.devicesmanagement.exceptions;

/**
 * 
 */
public class DuplicateHostNameException extends RuntimeException {
    public DuplicateHostNameException(String hostName) {
        super("A device with hostname '" + hostName + "' already exists.");
    }
}
