package us.muit.fs.devicesmanagement.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import us.muit.fs.devicesmanagement.entities.Device;
import us.muit.fs.devicesmanagement.entities.NetworkConf;
import us.muit.fs.devicesmanagement.exceptions.*;

public class InventoryTest {
    private Persistence persistence;
    private Inventory inventory;
    private Map<String, Device> db;

    @BeforeEach
    public void setUp() {
        persistence = mock(Persistence.class);
        db = new HashMap<>();
        when(persistence.load()).thenReturn(db);
        inventory = new Inventory(persistence);
    }

    @Test
    public void testUpdateDevice_savesChangesOnce() throws Exception {
        Device dev = new Device();
        dev.setId("dev1");
        dev.setHostname("host1");
        NetworkConf conf = new NetworkConf();
        conf.setIpAddress("192.168.0.1");
        conf.setMacAddress("AA:BB:CC:DD:EE:01");
        dev.setNetworkConf(conf);

        db.put(dev.getId(), dev); 

        Device updated = new Device();
        updated.setId("dev1");
        updated.setHostname("host1_updated");
        NetworkConf newConf = new NetworkConf();
        newConf.setIpAddress("192.168.0.2");
        newConf.setMacAddress("AA:BB:CC:DD:EE:02");
        updated.setNetworkConf(newConf);

        inventory.updateDevice(updated);

        verify(persistence, times(1)).save(db);
    }

    @Test
    public void testUpdateDevice_failsDueToDuplicateIpAddress() {
        Device existing = new Device();
        existing.setId("dev1");
        NetworkConf conf1 = new NetworkConf();
        conf1.setIpAddress("192.168.0.100");
        conf1.setMacAddress("AA:BB:CC:DD:EE:01");
        existing.setNetworkConf(conf1);
        db.put(existing.getId(), existing);

        Device conflicting = new Device();
        conflicting.setId("dev2");
        NetworkConf conf2 = new NetworkConf();
        conf2.setIpAddress("192.168.0.100"); 
        conf2.setMacAddress("AA:BB:CC:DD:EE:02");
        conflicting.setNetworkConf(conf2);

        db.put(conflicting.getId(), conflicting);

        assertThrows(DuplicateIpAddressException.class, () -> {
            inventory.updateDevice(conflicting); 
        });
    }
}
