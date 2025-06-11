/**
 * 
 */
package us.muit.fs.devicesmanagement.controllers;

import java.util.ArrayList;
/**
 * 
 */
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.muit.fs.devicesmanagement.entities.Device;
import us.muit.fs.devicesmanagement.entities.NetworkConf;
import us.muit.fs.devicesmanagement.exceptions.DeviceNotFoundException;
import us.muit.fs.devicesmanagement.exceptions.DuplicateDeviceException;
import us.muit.fs.devicesmanagement.exceptions.DuplicateHostNameException;
import us.muit.fs.devicesmanagement.exceptions.DuplicateIpAddressException;
import us.muit.fs.devicesmanagement.exceptions.DuplicateMacAddressException;
import us.muit.fs.devicesmanagement.entities.Application;

public class Inventory implements InventoryManager {
	private static String autoid = "1";
	private Map<String, Device> devices;

	private Persistence persistence;

	public Inventory(Persistence persistence) {
		this.persistence = persistence;
		this.devices = persistence.load(); // recupera el estado persistente
		if (this.devices == null) {
			this.devices = new HashMap<>();
		}
	}

	@Override
	public void addDevice(Device device) throws DuplicateMacAddressException, DuplicateIpAddressException,
			DuplicateMacAddressException, DuplicateDeviceException, DuplicateHostNameException {
		String id = device.getId();
		// Verifica ID no dulicado
		if (id != null && !id.equals("")) {
			if (devices.get(id) != null) {
				throw new DuplicateDeviceException(id);
			}
		}
		// Verifica MAC no duplicada
		String newMac = device.getNetworkConf().getMacAddress();
		if (newMac != null && !newMac.equals("")) {
			if (getDeviceByMac(newMac) != null) {
				throw new DuplicateMacAddressException(newMac);
			}
		}
		// Verifica IP no duplicada
		String newIp = device.getNetworkConf().getIpAddress();
		if (newIp != null && !newIp.equals("")) {
			if (getDeviceByIp(newIp) != null) {
				throw new DuplicateIpAddressException(newIp);
			}
		}
		// Verifica hostname no duplicado
		String newHN = device.getHostname();
		if (getDeviceByHostName(newHN) != null && !newHN.equals("")) {
			throw new DuplicateHostNameException(newHN);
		}
		// Añade id por defecto, si no tiene
		if (id == null || id.equals("")) {
			if (device.getHostname() != null && !device.getHostname().equals("")) {
				device.setId("dev-" + device.getHostname().toLowerCase().replaceAll("[^a-zA-Z0-9]", "-"));
			} else {
				device.setId(autoid);
				Integer tmp = Integer.valueOf(autoid);
				tmp++;
				autoid = tmp.toString();
			}
		}
		devices.put(device.getId(), device);
		persistence.save(devices);
	}

	@Override
	public Device newDevice(String hostName) throws DuplicateHostNameException {
		Device device = getDeviceByHostName(hostName);
		if (device != null) {
			throw new DuplicateHostNameException(hostName);
		}
		device = new Device();
		device.setHostname(hostName);
		// Crear un ID único
		device.setId("dev-" + hostName.toLowerCase().replaceAll("[^a-zA-Z0-9]", "-"));
		addDevice(device);
		return device;
	}

	@Override
	public void updateDevice(Device device) throws DuplicateHostNameException, DuplicateIpAddressException,
			DuplicateMacAddressException, DeviceNotFoundException {
		

		// Verifico que hostname sigue siendo único
		for (Device other : devices.values()) {
			if (!other.getId().equals(device.getId()) && device.getHostname() != null
					&& device.getHostname().equals(other.getHostname())) {
				throw new DuplicateHostNameException(device.getHostname());
			}
		}
		// verifico que la dir mac sigue siendo única
		NetworkConf conf = device.getNetworkConf();
		if (conf != null) {
			// Validar MAC
			String newMac = conf.getMacAddress();
			if (newMac != null && !newMac.equals("")) {
				for (Device other : devices.values()) {
					if (!other.getId().equals(device.getId())
							&& newMac.equals(other.getNetworkConf().getMacAddress())) {
						throw new DuplicateMacAddressException(newMac);
					}
				}
			}

			// Verifico que la ip sigue siendo única
			String newIp = conf.getIpAddress();
			if (newIp != null && !newIp.equals("")) {
				for (Device other : devices.values()) {
					if (!other.getId().equals(device.getId()) && newIp.equals(other.getNetworkConf().getMacAddress())) {
						throw new DuplicateIpAddressException(newIp);
					}
				}
			}
		}

		devices.put(device.getId(), device);
	
	}

	@Override
	public void removeDeviceById(String id) throws DeviceNotFoundException {
		if (!devices.containsKey(id)) {
			throw new DeviceNotFoundException(id);
		}

		devices.remove(id);
		persistence.save(devices);
	}

	@Override
	public Device getDeviceById(String id) {
		return devices.get(id);
	}

	@Override
	public Device getDeviceByHostName(String hostName) {
		Device dev = null;
		for (Device device : devices.values()) {
			if (device.getHostname() != null && device.getHostname().equals(hostName)) {
				dev = device;
				break;
			}
		}
		return dev;
	}

	@Override
	public Device getDeviceByIp(String ip) {
		Device dev = null;
		if (ip != null) {

			for (Device device : devices.values()) {

				if (ip.equals(device.getNetworkConf().getIpAddress())) {
					dev = device;
				}
			}
		}
		return dev;
	}

	@Override
	public Device getDeviceByMac(String mac) {
		Device dev = null;
		if (mac != null) {

			for (Device device : devices.values()) {

				if (mac.equals(device.getNetworkConf().getMacAddress())) {
					dev = device;
				}
			}
		}
		return dev;
	}

	@Override
	public Collection<Device> getAllDevices() {
		return devices.values();
	}

	@Override
	public List<Device> findDeviceByApp(String name) {
		List<Device> result = new ArrayList<>();
		for (Device device : devices.values()) {
			for (Application app : device.getApplications()) {
				if (app.getName() != null && app.getName().equals(name)) {
					result.add(device);
					break; // no hace falta seguir buscando en este device
				}
			}
		}
		return result;
	}

	@Override
	public List<Device> findDeviceByAppVersion(String name, String version) {
		List<Device> result = new ArrayList<>();
		for (Device device : devices.values()) {
			for (Application app : device.getApplications()) {
				if (app.getName() != null && app.getVersion() != null && app.getName().equals(name)
						&& app.getVersion().equals(version)) {
					result.add(device);
					break;
				}
			}
		}
		return result;
	}

}
