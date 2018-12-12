package com.nvg.exam.phannguyen.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by phannguyen on 7/29/17.
 */

@Scope
@Retention(RUNTIME)
public @interface ActivityScope {

}
