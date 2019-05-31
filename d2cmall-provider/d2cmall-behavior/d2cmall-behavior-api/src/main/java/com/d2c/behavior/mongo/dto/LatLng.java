package com.d2c.behavior.mongo.dto;

import com.d2c.common.api.dto.BaseDTO;

/**
 * 经纬度
 *
 * @author wull
 */
public class LatLng extends BaseDTO {

    private static final long serialVersionUID = 3636606228261721008L;
    /**
     * 纬度
     */
    private double latitude;
    /**
     * 经度
     */
    private double longitude;

    public LatLng() {
    }

    public LatLng(double lat, double lng) {
        this.latitude = lat;
        this.longitude = lng;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
