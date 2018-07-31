package com.sumerge.grad.program.jpa.rest.course;

import com.sumerge.grad.program.jpa.repositories.entity.Course;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

import static com.sumerge.grad.program.jpa.constants.Constants.PERSISTENT_UNIT;
import static java.util.logging.Level.SEVERE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("courses")
@Produces(APPLICATION_JSON)
public class CourseResources {

    private static final Logger LOGGER = Logger.getLogger(CourseResources.class.getName());

    @PersistenceContext(unitName = PERSISTENT_UNIT)
    private EntityManager em;

    @GET
    public Response getAllCourses() {
        try {
            return Response.ok().
                    entity(em.createQuery("SELECT c FROM Course c" +
                            " JOIN FETCH c.instructors" +
                            " JOIN FETCH c.students", Course.class).
                            getResultList()).
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e).
                    build();
        }
    }
}
