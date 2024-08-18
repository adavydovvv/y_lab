package ru.ylab.utils;

public interface Validator<T> {
    boolean validate(T object) throws ValidationException;
}
