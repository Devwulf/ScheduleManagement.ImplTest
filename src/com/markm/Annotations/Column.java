package com.markm.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// We want to be able to access this annotation during runtime
@Retention(RetentionPolicy.RUNTIME)
// We want this annotation to be used only on fields
@Target({ElementType.FIELD})
public @interface Column
{
    String name();
}
