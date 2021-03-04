package com.iyxan23.reflection.test;

import androidx.annotation.NonNull;

public class Attribute {
    public static enum Type {
        METHOD,
        FIELD,
        SUBCLASS
    }

    String str_representation;
    Type type;

    public Attribute(String str_representation, Type type) {
        this.str_representation = str_representation;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @NonNull
    @Override
    public String toString() {
        return str_representation;
    }
}
