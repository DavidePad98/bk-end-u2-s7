package bkendu2s7.u2s7.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@Table(name = "dipendenti")
public class Employee {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private long id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String avatar;
    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<Device> devicesAssigned;

    public Employee(String username, String name, String surname, String email, String password, String avatar) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public Employee() {
    }
}
