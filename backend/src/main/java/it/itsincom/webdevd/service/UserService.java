package it.itsincom.webdevd.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import io.vertx.ext.auth.User;
import it.itsincom.webdevd.persistence.UserRepository;
import it.itsincom.webdevd.persistence.model.ApplicationUser;
import it.itsincom.webdevd.web.model.CreateUserRequest;
import it.itsincom.webdevd.web.model.UserResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        String hash = BcryptUtil.bcryptHash(request.getPassword());

        ApplicationUser user = new ApplicationUser(
                request.getUsername(),
                hash,
                request.getRole().toString(),
                request.getFirstName(),
                request.getSecondName(),
                request.getAddress()
        );

        userRepository.persist(user);

        return toUserResponse(user);

    }

    public UserResponse authenticate(String username, String password) {
        ApplicationUser user = userRepository.authenticate(username, password);
        if (user != null) {
            return toUserResponse(user);
        }
        return null;
    }

    public List<UserResponse> findAll() {
        List<ApplicationUser> allUsers = userRepository.findAll(Sort.by("username"))
                .list();
        List<UserResponse> result = new ArrayList<>();
        for (ApplicationUser user : allUsers) {
            result.add(toUserResponse(user));
        }
        return result;
    }

    public UserResponse findById(int id) {
        UserResponse result = toUserResponse(userRepository.findById(id));
        return result;
    }

    private static UserResponse toUserResponse(ApplicationUser user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getFirstName(),
                user.getSecondName(),
                user.getAddress()
        );
    }

    public UserResponse getUserByUsername(String username) {
        return toUserResponse(userRepository.findByUsername(username));
    }

    @Transactional
    public boolean updateUser(int id, ApplicationUser request) {
        int modify = userRepository.update("UPDATE ApplicationUser u " +
                        "SET u.username = :username, " +
                        "u.firstName = :firstName, " +
                        "u.secondName = :secondName, " +
                        "u.address = :address " +
                        "WHERE u.id = :id",
                Parameters.with("username", request.getUsername())
                        .and("firstName", request.getFirstName())
                        .and("secondName", request.getSecondName())
                        .and("address", request.getAddress())
                        .and("id", id));
        request.setId(id);
        return modify > 0;
    }

    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
