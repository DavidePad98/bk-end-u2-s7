package bkendu2s7.u2s7.services;

import bkendu2s7.u2s7.entities.Employee;
import bkendu2s7.u2s7.exceptions.UnauthorizedException;
import bkendu2s7.u2s7.payloads.EmployeeLoginDTO;
import bkendu2s7.u2s7.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    @Autowired
    private EmployeeService es;

    @Autowired
    private JWTTools jwtTools;

    public String authenticateUserAndGenerateToken(EmployeeLoginDTO payload) {
        Employee employee = this.es.findByEmail(payload.email());
        if (employee.getPassword().equals(payload.password())) {
            return jwtTools.createToken(employee);
        } else {
            throw new UnauthorizedException("Credenziali non valide! Effettua di nuovo il login!");
        }
    }
}
