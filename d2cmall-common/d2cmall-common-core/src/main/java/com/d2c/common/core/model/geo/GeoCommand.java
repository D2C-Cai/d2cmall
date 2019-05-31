package com.d2c.common.core.model.geo;

import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Polygon;
import org.springframework.data.geo.Shape;
import org.springframework.util.Assert;

import static org.springframework.util.ObjectUtils.nullSafeEquals;
import static org.springframework.util.ObjectUtils.nullSafeHashCode;

public class GeoCommand {

    private final Shape shape;
    private final String command;

    /**
     * Creates a new {@link GeoCommand}.
     *
     * @param shape must not be {@literal null}.
     */
    public GeoCommand(Shape shape) {
        Assert.notNull(shape, "Shape must not be null!");
        this.shape = shape;
        this.command = getCommand(shape);
    }

    /**
     * @return the shape
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Returns the MongoDB command for the given {@link Shape}.
     *
     * @param shape must not be {@literal null}.
     * @return
     */
    private String getCommand(Shape shape) {
        Assert.notNull(shape, "Shape must not be null!");
        if (shape instanceof Box) {
            return "$box";
        } else if (shape instanceof Circle) {
            return "$center";
        } else if (shape instanceof Polygon) {
            return "$polygon";
        } else if (shape instanceof Sphere) {
            return Sphere.COMMAND;
        }
        throw new IllegalArgumentException("Unknown shape: " + shape);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = 31;
        result += 17 * nullSafeHashCode(this.command);
        result += 17 * nullSafeHashCode(this.shape);
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GeoCommand)) {
            return false;
        }
        GeoCommand that = (GeoCommand) obj;
        return nullSafeEquals(this.command, that.command) && nullSafeEquals(this.shape, that.shape);
    }

}

