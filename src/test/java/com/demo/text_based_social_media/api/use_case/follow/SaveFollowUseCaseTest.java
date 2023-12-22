package com.demo.text_based_social_media.api.use_case.follow;

import com.demo.text_based_social_media.api.follow.adapter.in.dto.FollowDto;
import com.demo.text_based_social_media.api.follow.adapter.out.FollowAdapter;
import com.demo.text_based_social_media.api.follow.application.FollowService;
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
public class SaveFollowUseCaseTest {

    @Autowired
    private FollowService followService;

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
        FollowDto followDto = new FollowDto("following@demo.com");
        followService.saveFollow(followDto, followerId);
    }

    @Test
    void shouldSaveFollow() {
        Assertions.assertNotNull(followAdapter.getFollowByFollowerIdAndFollowingId(followerId, followingId));
    }

    @Test
    void shouldNotFollowYourself() {
        FollowDto followDto = new FollowDto("follower@demo.com");
        Assertions.assertThrows(ResponseStatusException.class, () -> followService.saveFollow(followDto, followerId));
    }

    @Test
    void shouldNotSomeoneYouAlreadyFollow() {
        FollowDto followDto = new FollowDto("following@demo.com");
        Assertions.assertThrows(ResponseStatusException.class, () -> followService.saveFollow(followDto, followerId));
    }

    @Test
    void shouldDeleteFollow() {
        FollowDto followDto = new FollowDto("following@demo.com");
        followService.deleteFollow(followDto, followerId);
        Assertions.assertThrows(ResponseStatusException.class, () -> followAdapter.getFollowByFollowerIdAndFollowingId(followerId, followingId));
    }

    @Test
    void shouldNotDeleteFollowerWhenNotFollow() {
        SignUpRequest signUpRequest = new SignUpRequest("user@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest);
        FollowDto followDto = new FollowDto("user@demo.com");
        Assertions.assertThrows(ResponseStatusException.class, () -> followService.deleteFollow(followDto, followerId));
    }

    @Test
    void shouldNotDeleteInvalidUser() {
        FollowDto followDto = new FollowDto("user@demo.com");
        Assertions.assertThrows(ResponseStatusException.class, () -> followService.deleteFollow(followDto, followerId));
    }
}
