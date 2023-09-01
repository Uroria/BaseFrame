package com.uroria.base.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.uroria.base.gson.annotations.GsonTransient;

public final class AnnotationExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getAnnotation(GsonTransient.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
