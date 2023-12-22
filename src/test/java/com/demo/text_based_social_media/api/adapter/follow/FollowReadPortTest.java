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

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FollowReadPortTest {

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

    private Long followId;

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
        followId = followAdapter.getFollowByFollowerIdAndFollowingId(followerId, followingId).getId();
    }

    @Test
    void shouldGetFollowById() {
        Follow follow = followAdapter.getFollowById(followId);
        Assertions.assertNotNull(follow);
        Assertions.assertEquals(follow.getFollowerId(), followerId);
        Assertions.assertEquals(follow.getFollowingId(), followingId);
    }

    @Test
    void shouldGetFollowByValidIds() {
        Follow follow = followAdapter.getFollowByFollowerIdAndFollowingId(followerId, followingId);
        Assertions.assertNotNull(follow);
        Assertions.assertEquals(follow.getFollowerId(), followerId);
        Assertions.assertEquals(follow.getFollowingId(), followingId);
    }

    @Test
    void shouldThrowErrorInvalidFollowing() {
        Assertions.assertThrows(ResponseStatusException.class, () -> followAdapter.getFollowByFollowerIdAndFollowingId(followerId, followingId + 1));
    }

    @Test
    void shouldGetAllFollowsByUserId() {
        SignUpRequest signUpRequest = new SignUpRequest("following2@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest);
        Long following2Id = userAdapter.readUserByEmail(signUpRequest.getEmail()).getId();
        Follow follow = new Follow(null, followerId, following2Id, null, null);
        followAdapter.save(follow);
        List<Follow> allFollows = followAdapter.getAllByFollowerId(followerId);
        Assertions.assertEquals(allFollows.size(), 2);
        allFollows.forEach(follow1 -> {
            if (follow1.getFollowingId().longValue() == followingId)
                Assertions.assertEquals(userAdapter.readUserById(follow1.getFollowingId()).getEmail(), userAdapter.readUserById(followingId).getEmail());
            if (follow1.getFollowingId().longValue() == following2Id)
                Assertions.assertEquals(userAdapter.readUserById(follow1.getFollowingId()).getEmail(), userAdapter.readUserById(following2Id).getEmail());
        });
    }

    @Test
    void shouldGetAllFollowingUser() {
        Follow follow = new Follow(null, followingId, followerId, null, null);
        followAdapter.save(follow);
        Assertions.assertEquals(followAdapter.getAllByFollowingId(followerId).size(), 1);
        SignUpRequest signUpRequest = new SignUpRequest("following2@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest);
        Long following2Id = userAdapter.readUserByEmail(signUpRequest.getEmail()).getId();
        Follow follow2 = new Follow(null, following2Id, followerId, null, null);
        followAdapter.save(follow2);
        List<Follow> allFollows = followAdapter.getAllByFollowingId(followerId);
        Assertions.assertEquals(allFollows.size(), 2);
        allFollows.forEach(follow3 -> {
            if (follow3.getFollowerId().longValue() == followingId)
                Assertions.assertEquals(userAdapter.readUserById(follow3.getFollowerId()).getEmail(), userAdapter.readUserById(followingId).getEmail());
            if (follow3.getFollowerId().longValue() == following2Id)
                Assertions.assertEquals(userAdapter.readUserById(follow3.getFollowerId()).getEmail(), userAdapter.readUserById(following2Id).getEmail());
        });
    }
}
