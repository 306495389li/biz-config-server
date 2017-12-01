package com.flaginfo.lightning.common;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.engine.spi.SessionImplementor;

public class IdUtil implements IdentifierGenerator {

    @Override
    public Serializable generate(SessionImplementor arg0, Object arg1)
            throws HibernateException {
        return Long.valueOf(IDGen.nextId());
    }

}
