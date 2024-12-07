package Modelo;

import java.time.LocalDate;

public class Convidado extends Usuario{

    public Convidado() {
        super("NULO", LocalDate.now(), "Convidado", "NULO", "NULA");
    }
}
