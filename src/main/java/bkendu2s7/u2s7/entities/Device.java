package bkendu2s7.u2s7.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@Table(name = "dispositivi")
public class Device {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private long id;
    private String type;
    private String status;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Device(String type, String status, Employee employee) {
        this.type = type;
        this.status = status;
        this.employee = employee;
    }

    public Device() {
    }
}
