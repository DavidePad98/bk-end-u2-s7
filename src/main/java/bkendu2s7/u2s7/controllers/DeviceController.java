package bkendu2s7.u2s7.controllers;

import bkendu2s7.u2s7.entities.Device;
import bkendu2s7.u2s7.exceptions.BadRequestException;
import bkendu2s7.u2s7.payloads.DeviceDTO;
import bkendu2s7.u2s7.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private DeviceService ds;

    @GetMapping
    private Page<Device> getAllDevices(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sort) {
        return ds.getDevices(page, size, sort);
    }

    @GetMapping("/{id}")
    private Device getDeviceById(@PathVariable long id) {
        return ds.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Device saveNewDevice(@RequestBody @Validated DeviceDTO payload, BindingResult validation) {
        if (validation.hasErrors()) throw new BadRequestException(validation.getAllErrors());
        else return ds.save(payload);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteDevice(@PathVariable long id) {
        ds.findByIdAndDelete(id);
    }

    @PutMapping("/{id}")
    private Device updateDevice(@PathVariable long id, @RequestBody @Validated DeviceDTO payload, BindingResult validation) {
        if (validation.hasErrors()) throw new BadRequestException(validation.getAllErrors());
        else return ds.findByIdAndUpdate(id, payload);
    }


    @PutMapping("/{deviceId}/assign/{employeeId}")
    public Device assignEmployeeToDevice(@PathVariable Long deviceId, @PathVariable Long employeeId) {
        return ds.assignEmployeeToDevice(deviceId, employeeId);
    }

    // HO TROVATO QUESTO @ResponseEntity CHE RESTITUISCE BODY, HEADER E LO STATO DELLA RISPOSTA, NON LO AVEVO MAI USATO
    //L' HO PROVATO E SEMBRA FUNZIONARE COME L'ALTRO METODO CHE HO SCRITTO
//    @PutMapping("/{deviceId}/assign/{employeeId}")
//    public ResponseEntity<Device> assignDeviceToEmployee(@PathVariable Long deviceId, @PathVariable Long employeeId){
//        Device updatedDevice = ds.assignEmployeeToDevice(deviceId, employeeId);
//        return ResponseEntity.ok(updatedDevice);
//    }
}