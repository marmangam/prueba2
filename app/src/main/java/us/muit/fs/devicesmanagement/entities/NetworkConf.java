/**
 * 
 */
package us.muit.fs.devicesmanagement.entities;

/**
 * Network configuration information
 */
public class NetworkConf {
    private String ipAddress;
    private String macAddress;
    private String gateway;

    public NetworkConf() {
    	  this.ipAddress = "";
          this.macAddress = "";
          this.gateway = "";
    }

    public NetworkConf(String ipAddress, String macAddress, String gateway) {
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.gateway = gateway;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }
}

