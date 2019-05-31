package com.d2c.common.core.model.geo;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Shape;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

public class Sphere implements Shape {

    public static final String COMMAND = "$centerSphere";
    private static final long serialVersionUID = -3987100230618634466L;
    private final Point center;
    private final Distance radius;

    /**
     * Creates a Sphere around the given center {@link Point} with the given radius.
     *
     * @param center must not be {@literal null}.
     * @param radius must not be {@literal null}.
     */
    @PersistenceConstructor
    public Sphere(Point center, Distance radius) {
        Assert.notNull(center, "Center point must not be null!");
        Assert.notNull(radius, "Radius must not be null!");
        Assert.isTrue(radius.getValue() >= 0, "Radius must not be negative!");
        this.center = center;
        this.radius = radius;
    }

    /**
     * Creates a Sphere around the given center {@link Point} with the given radius.
     *
     * @param center
     * @param radius
     */
    public Sphere(Point center, double radius) {
        this(center, new Distance(radius));
    }

    /**
     * Creates a Sphere from the given {@link Circle}.
     *
     * @param circle
     */
    public Sphere(Circle circle) {
        this(circle.getCenter(), circle.getRadius());
    }

    /**
     * Returns the center of the {@link Circle}.
     *
     * @return will never be {@literal null}.
     */
    public Point getCenter() {
        return new Point(this.center);
    }

    /**
     * Returns the radius of the {@link Circle}.
     *
     * @return
     */
    public Distance getRadius() {
        return radius;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Sphere [center=%s, radius=%s]", center, radius);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Sphere)) {
            return false;
        }
        Sphere that = (Sphere) obj;
        return this.center.equals(that.center) && this.radius.equals(that.radius);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = 17;
        result += 31 * center.hashCode();
        result += 31 * radius.hashCode();
        return result;
    }

    /**
     * Returns the {@link Shape} as a list of usually {@link Double} or {@link List}s of {@link Double}s. Wildcard bound
     * to allow implementations to return a more concrete element type.
     *
     * @return
     */
    public List<? extends Object> asList() {
        return Arrays.asList(Arrays.asList(center.getX(), center.getY()), this.radius.getValue());
    }

    /**
     * Returns the command to be used to create the {@literal $within} criterion.
     *
     * @return
     */
    public String getCommand() {
        return COMMAND;
    }

}

