package com.d2c.common.api.model;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * 实体类 - 基类
 */
@Entity
public abstract class BaseParentDO<ID> implements Serializable {

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
        final BaseParentDO other = (BaseParentDO) obj;
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
