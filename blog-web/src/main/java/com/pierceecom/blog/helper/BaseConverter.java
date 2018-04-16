package com.pierceecom.blog.helper;

import java.util.Collection;
import java.util.stream.Collectors;

@FunctionalInterface
public interface BaseConverter<F, T> {

    T convert(F from);

    default Collection<T> convertAll(Collection<F> elements) {
        return elements.stream() //
                .map(this::convert) //
                .collect(Collectors.toList());
    }
}
