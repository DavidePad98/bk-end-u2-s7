package bkendu2s7.u2s7.controllers;

import bkendu2s7.u2s7.entities.Employee;
import bkendu2s7.u2s7.exceptions.BadRequestException;
import bkendu2s7.u2s7.payloads.EmployeeDTO;
import bkendu2s7.u2s7.payloads.EmployeeLoginDTO;
import bkendu2s7.u2s7.payloads.EmployeeLoginResponseDTO;
import bkendu2s7.u2s7.payloads.NewEmployeeResponseDTO;
import bkendu2s7.u2s7.services.AuthorizationService;
import bkendu2s7.u2s7.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    private AuthorizationService authorService;

    @Autowired
    EmployeeService es;

    @PostMapping("/login")
    public EmployeeLoginResponseDTO login(@RequestBody EmployeeLoginDTO payload){
        return new EmployeeLoginResponseDTO(this.authorService.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    private NewEmployeeResponseDTO saveEmployee(@RequestBody @Validated EmployeeDTO payload, BindingResult validation) {
        if (validation.hasErrors()) throw new BadRequestException(validation.getAllErrors());
        return new NewEmployeeResponseDTO(this.es.save(payload).getId());
    }
}
