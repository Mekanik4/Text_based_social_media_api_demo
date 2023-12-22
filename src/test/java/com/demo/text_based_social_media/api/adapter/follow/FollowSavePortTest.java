package com.demo.text_based_social_media.api.adapter.follow;

import com.demo.text_based_social_media.api.follow.adapter.out.FollowAdapter;
import com.demo.text_based_social_media.api.follow.domain.Follow;
import com.demo.text_based_social_media.api.role.application.RoleService;
import com.demo.text_based_social_media.api.role.domain.Role;
import com.demo.text_based_social_media.api.user.authentication.application.SignUpService;
import com.demo.text_based_social_media.api.user.authentication.domain.SignUpRequest;
import com.demo.text_based_social_media.api.user.details.adapter.out.UserAdapter;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FollowSavePortTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private UserAdapter userAdapter;

    @Autowired
    private FollowAdapter followAdapter;

    private Long followerId;

    private Long followingId;

    @BeforeEach
    public void setUp() {
        Role role = new Role();
        role.setName("USER");
        roleService.saveRole(role);
        SignUpRequest signUpRequest = new SignUpRequest("follower@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest);
        followerId = userAdapter.readUserByEmail(signUpRequest.getEmail()).getId();
        SignUpRequest signUpRequest2 = new SignUpRequest("following@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest2);
        followingId = userAdapter.readUserByEmail(signUpRequest2.getEmail()).getId();
        Follow follow = new Follow(null, followerId, followingId, null, null);
        followAdapter.save(follow);
    }

    @Test
    void shouldSaveFollow() {
        Assertions.assertNotNull(followAdapter.getFollowByFollowerIdAndFollowingId(followerId, followingId));
    }

    @Test
    void shouldNotSomeoneYouAlreadyFollow() {
        Follow follow = new Follow(null, followerId, followingId, userAdapter.readUserById(followerId), userAdapter.readUserById(followingId));
        Assertions.assertThrows(ResponseStatusException.class, () -> followAdapter.save(follow));
    }

    @Test
    void shouldDeleteByUserIds() {
        followAdapter.delete(followerId, followingId);
        Assertions.assertThrows(ResponseStatusException.class, () -> followAdapter.getFollowByFollowerIdAndFollowingId(followerId, followingId));
    }

    @Test
    void shouldNotDeleteFollowerWhenNotFollow() {
        SignUpRequest signUpRequest = new SignUpRequest("user@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest);
        Long newUserId = userAdapter.readUserByEmail(signUpRequest.getEmail()).getId();
        Assertions.assertThrows(ResponseStatusException.class, () -> followAdapter.delete(followerId, newUserId));
    }
}
