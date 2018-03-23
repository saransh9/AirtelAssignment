package com.airtel.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by saransh on 15/03/18.
 */

@Qualifier
@Retention(RetentionPolicy.CLASS)
public @interface ActivityContext {
}
