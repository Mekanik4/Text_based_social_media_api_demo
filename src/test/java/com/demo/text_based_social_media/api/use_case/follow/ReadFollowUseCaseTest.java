package com.demo.text_based_social_media.api.use_case.follow;

import com.demo.text_based_social_media.api.follow.adapter.in.dto.FollowDto;
import com.demo.text_based_social_media.api.follow.adapter.out.FollowAdapter;
import com.demo.text_based_social_media.api.follow.application.FollowService;
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

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ReadFollowUseCaseTest {

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
        FollowDto followDto = new FollowDto("following@demo.com");
        followService.saveFollow(followDto, followerId);
        followId = followAdapter.getFollowByFollowerIdAndFollowingId(followerId, followingId).getId();
    }

    @Test
    void shouldReadFollowById() {
        Follow follow = followService.getFollowById(followId);
        Assertions.assertNotNull(follow);
        Assertions.assertEquals(follow.getFollowerId(), followerId);
        Assertions.assertEquals(follow.getFollowingId(), followingId);
    }

    @Test
    void shouldGetAllFollowsByUserId() {
        SignUpRequest signUpRequest = new SignUpRequest("following2@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest);
        Long following2Id = userAdapter.readUserByEmail(signUpRequest.getEmail()).getId();
        FollowDto followDto = new FollowDto(signUpRequest.getEmail());
        followService.saveFollow(followDto, followerId);
        List<Follow> allFollows = followService.getAllByFollowerId(followerId);
        Assertions.assertEquals(allFollows.size(), 2);
        allFollows.forEach(follow -> {
            if (follow.getFollowingId().longValue() == followingId)
                Assertions.assertEquals(follow.getFollowing().getEmail(), userAdapter.readUserById(followingId).getEmail());
            if (follow.getFollowingId().longValue() == following2Id)
                Assertions.assertEquals(follow.getFollowing().getEmail(), userAdapter.readUserById(following2Id).getEmail());
        });
    }

    @Test
    void shouldGetAllFollowingUser() {
        FollowDto followDto = new FollowDto("follower@demo.com");
        followService.saveFollow(followDto, followingId);
        Assertions.assertEquals(followService.getAllByFollowingId(followerId).size(), 1);
        SignUpRequest signUpRequest = new SignUpRequest("following2@demo.com", "12345678", "USER", false);
        signUpService.signUpUser(signUpRequest);
        Long following2Id = userAdapter.readUserByEmail(signUpRequest.getEmail()).getId();
        FollowDto followDto2 = new FollowDto("follower@demo.com");
        followService.saveFollow(followDto2, following2Id);
        List<Follow> allFollows = followService.getAllByFollowingId(followerId);
        Assertions.assertEquals(allFollows.size(), 2);
        allFollows.forEach(follow -> {
            if (follow.getFollowerId().longValue() == followingId)
                Assertions.assertEquals(userAdapter.readUserById(follow.getFollowerId()).getEmail(), userAdapter.readUserById(followingId).getEmail());
            if (follow.getFollowerId().longValue() == following2Id)
                Assertions.assertEquals(userAdapter.readUserById(follow.getFollowerId()).getEmail(), userAdapter.readUserById(following2Id).getEmail());
        });
    }
}
