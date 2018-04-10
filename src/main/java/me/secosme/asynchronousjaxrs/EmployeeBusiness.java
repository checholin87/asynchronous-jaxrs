package me.secosme.asynchronousjaxrs;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
public class EmployeeBusiness {

    @Asynchronous
    public void list(Consumer<List<EmployeeDTO>> callable) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        callable.accept(sync());
    }

    public List<EmployeeDTO> sync() {
        return Arrays.asList(
            new EmployeeDTO(UUID.randomUUID(), "Sara Connor"),
            new EmployeeDTO(UUID.randomUUID(), "John Connor"),
            new EmployeeDTO(UUID.randomUUID(), "Kyle Rise"));
    }
}
