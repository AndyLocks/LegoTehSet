package org.lts.lego_teh_set.cache;

import org.lts.lego_teh_set.rebrickableAPI.OrderingType;

/**
 * Represents a function that converts a request and
 * ordering type into key for redis cache.
 * <p>
 * This is a functional interface
 * whose functional method is {@link #convert(String, OrderingType)}
 */
@FunctionalInterface
public interface RequestAndOrderingTypeToKeyConverter {

    /**
     * Converts a request and order
     * type into key for redis cache.
     *
     * @param request      must not be null
     * @param orderingType must not be null
     * @return a string key for redis cache
     */
    String convert(String request, OrderingType orderingType);

    /**
     * Returns default converter
     *
     * <p>
     * Example: {@code request::order_type}
     *
     * @return a convert function
     */
    static RequestAndOrderingTypeToKeyConverter withDefaults() {
        return ((request, orderingType) -> String.format("%s::%s",
                request, orderingType.getJsonProperty()));
    }

}
