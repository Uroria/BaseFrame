package com.uroria.base.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GsonFactory {

    public Gson create() {
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        builder.registerTypeAdapterFactory(new Object2ObjectMapTypeAdapterFactory());
        builder.registerTypeAdapterFactory(new Object2BooleanMapTypeAdapterFactory());
        builder.registerTypeAdapterFactory(new Object2IntMapTypeAdapterFactory());

        builder.registerTypeAdapterFactory(new ObjectListTypeAdapterFactory());

        AnnotationExclusionStrategy exclusionStrategy = new AnnotationExclusionStrategy();
        builder.addSerializationExclusionStrategy(exclusionStrategy);
        builder.addDeserializationExclusionStrategy(exclusionStrategy);
        return builder.create();
    }
}
