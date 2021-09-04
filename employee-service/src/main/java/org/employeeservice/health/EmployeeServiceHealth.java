package org.employeeservice.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.employeeservice.entity.AcmeEmployee;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Readiness
public class EmployeeServiceHealth implements HealthCheck {

    @PersistenceContext(unitName = "EmployeeService_1.0PU")
    private EntityManager em;

    public HealthCheckResponse call() {
        final List<AcmeEmployee> acmeEmployees = em.createQuery("SELECT a FROM AcmeEmployee a", AcmeEmployee.class).getResultList();

        return HealthCheckResponse
                .named("database-employees-ready")
                .withData("numberOfEmployees", String.valueOf(acmeEmployees.size()))
                .up()
            .build();
    }
}