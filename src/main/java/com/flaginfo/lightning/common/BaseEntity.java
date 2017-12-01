package com.flaginfo.lightning.common;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author Administrator
 * 
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public abstract Long getPrimaryKey();

}