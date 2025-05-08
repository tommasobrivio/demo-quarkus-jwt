package it.itsincom.webdevd.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import it.itsincom.webdevd.persistence.model.ApplicationUser;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<ApplicationUser, Long> {
}
