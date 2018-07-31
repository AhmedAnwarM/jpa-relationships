package com.sumerge.grad.program.jpa.repositories.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.sumerge.grad.program.jpa.constants.Constants.PERSISTENT_UNIT;

@Stateless
public class Repository {


    @PersistenceContext(unitName = PERSISTENT_UNIT)
    private EntityManager em;

    public Object save(Object o) {
        return em.merge(o);
    }
}
