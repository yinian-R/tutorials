package com.wymm.coreenum.converter;

public interface Converter<S,T> {
    T convert(S var);
}
