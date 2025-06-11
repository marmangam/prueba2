/**
 * 
 */
package us.muit.fs.devicesmanagement.exceptions;

/**
 * 
 */
public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String id) {
        super("No device found with ID: " + id);
    }
}

