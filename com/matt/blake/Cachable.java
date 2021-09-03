package com.matt.blake;

import java.util.LinkedHashMap;

public interface Cachable {
    void setCache(LinkedHashMap<Integer, String> cache);

    void store();

    String retrieve(Object obj);
}
