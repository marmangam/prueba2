/**
 * 
 */
package us.muit.fs.devicesmanagement.exceptions;

/**
 * 
 */
public class DuplicateIpAddressException extends RuntimeException {
    public DuplicateIpAddressException(String ip) {
        super("A device with IP address '" + ip + "' already exists.");
    }
}

