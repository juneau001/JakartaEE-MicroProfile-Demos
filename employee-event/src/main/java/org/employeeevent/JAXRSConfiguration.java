package org.employeeevent;

import java.util.Set;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@ApplicationPath("rest")
public class JAXRSConfiguration extends Application {
        @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(org.employeeevent.service.SseEventBroadcaster.class);
        resources.add(org.employeeevent.service.SseEventResource.class);
        resources.add(org.employeeevent.resources.JavaEE8Resource.class);
        return resources;
    }
            
}
