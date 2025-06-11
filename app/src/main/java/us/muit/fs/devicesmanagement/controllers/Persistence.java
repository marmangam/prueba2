/**
 * 
 */
package us.muit.fs.devicesmanagement.controllers;

/**
 * 
 */
import java.util.Map;

import us.muit.fs.devicesmanagement.entities.Device;

public interface Persistence {
    /**
     * Guarda el mapa de dispositivos en el sistema de persistencia.
     * @param devices Mapa con los dispositivos a guardar.
     */
    void save(Map<String, Device> devices);

    /**
     * Recupera el mapa de dispositivos desde el sistema de persistencia.
     * @return Mapa con los dispositivos recuperados.
     */
    Map<String, Device> load();
}

