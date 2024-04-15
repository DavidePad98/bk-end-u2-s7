package bkendu2s7.u2s7.controllers;

import bkendu2s7.u2s7.entities.Employee;
import bkendu2s7.u2s7.exceptions.BadRequestException;
import bkendu2s7.u2s7.payloads.EmployeeDTO;
import bkendu2s7.u2s7.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService es;

    @GetMapping
    private Page<Employee> getEmployees(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sort) {
        return es.getEmployees(page, size, sort);
    }

    @GetMapping("/{id}")
    private Employee getEmployeeById(@PathVariable long id) {
        return es.findById(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteEmployee(@PathVariable long id) {
        es.findByIdAndDelete(id);
    }

    @PutMapping("/{id}")
    private Employee updateEmployee(@PathVariable long id, @RequestBody @Validated EmployeeDTO image, BindingResult validation) {
        if (validation.hasErrors()) throw new BadRequestException(validation.getAllErrors());
        else return es.findByIdAndUpdate(id, image);
    }

    @PostMapping("/{id}/updateAvatar")
    private Employee updateAuthorAvatar(@PathVariable long id, @RequestParam("avatar") MultipartFile image) throws IOException {
        return es.updateEmployeeAvatar(id, image);
    }
}
