package com.jana.products.exception;

public class ResourceNotFoundException extends IllegalArgumentException {

    public ResourceNotFoundException(String name, String value, Object obj) {
        super(String.format("%s was not found with %s = %s", name, value, obj));
    }
}
