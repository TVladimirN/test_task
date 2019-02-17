package ru.test.task.trafficstatistics.procesor.validator;

public interface ValidationProcessor<T> {

    boolean validation(T t);
}
