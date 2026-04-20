package com.smartcampus;

import com.smartcampus.exceptions.*;
import com.smartcampus.filters.LoggingFilter;
import com.smartcampus.resources.DiscoveryResource;
import com.smartcampus.resources.SensorReadingResource;
import com.smartcampus.resources.SensorResource;
import com.smartcampus.resources.SensorRoomResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api/v1")
public class SmartCampusApplication extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        
        // Register Resources
        classes.add(DiscoveryResource.class);
        classes.add(SensorRoomResource.class);
        classes.add(SensorResource.class);
        // Note: SensorReadingResource is dynamically instantiated via a Sub-Resource Locator inside SensorResource
        
        // Register Exception Mappers
        classes.add(RoomNotEmptyExceptionMapper.class);
        classes.add(LinkedResourceNotFoundExceptionMapper.class);
        classes.add(SensorUnavailableExceptionMapper.class);
        classes.add(GlobalExceptionMapper.class);
        
        // Register Filters
        classes.add(LoggingFilter.class);
        
        return classes;
    }
}
