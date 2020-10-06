package org.employeeevent;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

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
        return resources;
    }
            
}
