package com.d2c.common.api.dto;

/**
 * DTO - 基类
 */
public abstract class BaseParentDTO<ID> extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @Override
    public int hashCode() {
        return getId() == null ? System.identityHashCode(this) : getId().hashCode();
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass().getPackage() != obj.getClass().getPackage()) {
            return false;
        }
        final BaseParentDTO other = (BaseParentDTO) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!getId().equals(other.getId())) {
            return false;
        }
        return true;
    }
    // *****************************************

    public abstract ID getId();

    public abstract void setId(ID id);

}
