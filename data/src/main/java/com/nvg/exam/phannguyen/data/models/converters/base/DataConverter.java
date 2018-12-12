package com.nvg.exam.phannguyen.data.models.converters.base;

/**
 * Created by phannguyen on 7/29/17.
 */

public interface DataConverter<F,T> {
    T convert(F fromData);
}
