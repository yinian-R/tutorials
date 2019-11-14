package com.wymm.coreenum;

public interface Converter<S,T> {
    T convert(S var);
}
