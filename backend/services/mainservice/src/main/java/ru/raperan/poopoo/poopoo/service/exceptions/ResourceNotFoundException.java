package ru.raperan.poopoo.poopoo.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName) {
        super("Resource not found: " + resourceName);
    }

}
