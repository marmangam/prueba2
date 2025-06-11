/**
 * 
 */
package us.muit.fs.devicesmanagement.entities;

/**
 * Device Info
 */
import java.util.ArrayList;
import java.util.List;

public class Device {
    private String id;
    private String hostname;
    private OS os;
    private NetworkConf networkConf;
    private List<Application> applications;

    public Device() {
        this.applications = new ArrayList<>();
        this.networkConf=new NetworkConf();
    }

    public Device(String id, String hostname, OS os, NetworkConf networkConf) {
        this.id = id;
        this.hostname = hostname;
        this.os = os;
        this.networkConf = networkConf;
        this.applications = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public OS getOs() {
        return os;
    }

    public void setOs(OS os) {
        this.os = os;
    }

    public NetworkConf getNetworkConf() {
        return networkConf;
    }

    public void setNetworkConf(NetworkConf networkConf) {
        this.networkConf = networkConf;
    }
    
    public void setIp(String ip) {
    	this.networkConf.setIpAddress(ip);
    }
    public void setMac(String mac) {
    	this.networkConf.setMacAddress(mac);;
    }
    public void setGateway(String gw) {
    	this.networkConf.setGateway(gw);
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public void addApplication(Application application) {
        this.applications.add(application);
    }
    
    @Override
	public String toString() {
    	String me="Info del dispositivo: \n ID= "+this.id+"\n hostName= "+this.hostname+"\n ip= "+this.networkConf.getIpAddress()+"\n mac= "+this.networkConf.getMacAddress();
    	return me;
    }
}
