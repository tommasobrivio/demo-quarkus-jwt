package it.itsincom.webdevd.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
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
        ApplicationUser user = new ApplicationUser(
                request.getUsername(),
                request.getPassword(),
                request.getRole()
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

    private static UserResponse toUserResponse(ApplicationUser user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }

    public UserResponse getUserByUsername(String username) {
        return toUserResponse(userRepository.findByUsername(username));
    }
}
