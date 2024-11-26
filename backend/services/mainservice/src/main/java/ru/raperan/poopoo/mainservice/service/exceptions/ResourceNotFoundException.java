package ru.raperan.poopoo.mainservice.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName) {
        super("Resource not found: " + resourceName);
    }

}
