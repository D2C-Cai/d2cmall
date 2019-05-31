package com.d2c.common.core.model.geo;

/**
 * Interface definition for structures defined in GeoJSON ({@link http://geojson.org/}) format.
 *
 * @author Christoph Strobl
 * @since 1.7
 */
public interface GeoJson<T extends Iterable<?>> {

    /**
     * String value representing the type of the {@link GeoJson} object.
     *
     * @return will never be {@literal null}.
     * @see <a href="http://geojson.org/geojson-spec.html#geojson-objects">http://geojson.org/geojson-spec.html#geojson-objects</a>
     */
    String getType();

    /**
     * The value of the coordinates member is always an {@link Iterable}. The structure for the elements within is
     * determined by {@link #getType()} of geometry.
     *
     * @return will never be {@literal null}.
     * @see <a href="http://geojson.org/geojson-spec.html#geometry-objects">http://geojson.org/geojson-spec.html#geometry-objects</a>
     */
    T getCoordinates();

}
