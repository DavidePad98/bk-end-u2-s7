package bkendu2s7.u2s7.services;

import bkendu2s7.u2s7.dao.EmployeeDAO;
import bkendu2s7.u2s7.entities.Employee;
import bkendu2s7.u2s7.exceptions.BadRequestException;
import bkendu2s7.u2s7.exceptions.NotFoundException;
import bkendu2s7.u2s7.payloads.EmployeeDTO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDAO eDAO;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Employee save(EmployeeDTO emp){
        this.eDAO.findByEmail(emp.email()).ifPresent(
                employee -> {throw new BadRequestException("L'email " + emp.email() + " è già in uso!");
                }
        );
        Employee newEmployee = new Employee(
                emp.username(),
                emp.name(),
                emp.surname(),
                emp.email(),
                emp.password(),
                "https://ui-avatars.com/api/?name=" + emp.name() + "+" + emp.surname()
        );
                return eDAO.save(newEmployee);
    }

    public Page<Employee> getEmployees(int page, int size, String sort) {
        if(size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return eDAO.findAll(pageable);
    }

    public Employee findById(long id) {
        return eDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) {
        Employee found = this.findById(id);
        eDAO.delete(found);
    }

    public Employee findByIdAndUpdate(long id, EmployeeDTO newEmployee) {
        Employee found = this.findById(id);
        found.setAvatar("https://ui-avatars.com/api/?name=" + newEmployee.name() + "+" + newEmployee.surname());
        found.setUsername(newEmployee.username());
        found.setName(newEmployee.name());
        found.setSurname(newEmployee.surname());
        found.setEmail(newEmployee.email());
        eDAO.save(found);
        return found;
    }

    public Employee updateEmployeeAvatar(long id, MultipartFile image) throws IOException {
        Employee found = this.findById(id);
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(url);
        eDAO.save(found);
        return found;
    }

    public Employee findByEmail(String email){
        return eDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Dipendente non trovato"));
    }

}
