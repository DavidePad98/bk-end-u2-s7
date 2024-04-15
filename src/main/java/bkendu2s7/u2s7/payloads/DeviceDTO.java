package bkendu2s7.u2s7.payloads;

import jakarta.validation.constraints.*;

public record DeviceDTO(

        @NotEmpty(message = "devi mettere un tipo")
        @Size(min = 2, max = 15, message = "il nome deve contenere da un minimo di 2 caratteri and un massimo di 15")
        String type,

        @NotEmpty(message = "devi mettere uno stato")
        @Size(min = 2, max = 15, message = "il nome deve contenere da un minimo di 2 caratteri and un massimo di 15")
        String status
) {
}
