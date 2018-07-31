package com.sumerge.grad.program.jpa.rest.student;

import com.sumerge.grad.program.jpa.repositories.boundary.Repository;
import com.sumerge.grad.program.jpa.repositories.entity.Student;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.logging.Logger;

import static com.sumerge.grad.program.jpa.constants.Constants.PERSISTENT_UNIT;
import static java.util.logging.Level.SEVERE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@RequestScoped
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("students")
public class StudentResources {

    private static final Logger LOGGER = Logger.getLogger(StudentResources.class.getName());

    @EJB
    private Repository repo;

    @PersistenceContext(unitName = PERSISTENT_UNIT)
    private EntityManager em;

    @Context
    HttpServletRequest request;

    @GET
    public Response getAllStudents() {
        try {
            return Response.ok().
                    entity(em.createQuery("SELECT s FROM Student s" +
                            " LEFT JOIN FETCH s.addresses", Student.class).
                            getResultList()).
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e).
                    build();
        }
    }

    @GET
    @Path("{id}")
    public Response getStudent(@PathParam("id") Long id) {
        try {
            return Response.ok().
                    entity(em.find(Student.class, id)).
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e).
                    build();
        }
    }

    @POST
    public Response addStudent(Student student) {
        try {
            if (student.getId() != null)
                throw new IllegalArgumentException("Can't create student since it exists in the database");

            Student merged = (Student) repo.save(student);
            URI uri = new URI(request.getContextPath() + "students" + merged.getId());
            return Response.created(uri).
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e).
                    build();
        }
    }

    @PUT
    public Response editStudent(Student student) {
        try {
            if (student.getId() == null)
                throw new IllegalArgumentException("Can't edit student since it does not exist in the database");

            repo.save(student);
            return Response.ok().
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e).
                    build();
        }
    }
}
