package us.muit.fs.devicesmanagement.controllers;



import java.util.Collection;
import java.util.List;

import us.muit.fs.devicesmanagement.entities.Device;
import us.muit.fs.devicesmanagement.exceptions.DeviceNotFoundException;
import us.muit.fs.devicesmanagement.exceptions.DuplicateHostNameException;
import us.muit.fs.devicesmanagement.exceptions.DuplicateIpAddressException;
import us.muit.fs.devicesmanagement.exceptions.DuplicateMacAddressException;
import us.muit.fs.devicesmanagement.exceptions.DuplicateDeviceException;

public interface InventoryManager {
	// Introduce un dispositivo nuevo al inventario
	void addDevice(Device device) throws DuplicateMacAddressException, DuplicateIpAddressException,
			DuplicateMacAddressException, DuplicateDeviceException, DuplicateHostNameException;

	// Crea un dispositivo con un nombre de host, el resto de campos vacíos, y lo añade al inventario
	Device newDevice(String hostName) throws DuplicateHostNameException;

	// Actualiza un dispositivo ya existente en el inventario
	void updateDevice(Device device) throws DuplicateHostNameException, DuplicateIpAddressException,
			DuplicateMacAddressException, DeviceNotFoundException;

	// Elimina un dispositivo por id
	void removeDeviceById(String id) throws DeviceNotFoundException;

	// Localiza un dispositivo por id
	Device getDeviceById(String id);

	// Localiza un dispositivo por nombre del host
	Device getDeviceByHostName(String hostName);

	// Localiza un dispositivo por ip
	Device getDeviceByIp(String ip);

	// Localiza un dispositivo por mac
	Device getDeviceByMac(String mac);

	// Devuelve todos los dispositivos
	Collection<Device> getAllDevices();

	// Localiza todos los dispositivos que contienen una aplicación
	List<Device> findDeviceByApp(String name);

	// Localiza todos los dispositivos que contienen una aplicación en una versión
	List<Device> findDeviceByAppVersion(String name, String version);

}