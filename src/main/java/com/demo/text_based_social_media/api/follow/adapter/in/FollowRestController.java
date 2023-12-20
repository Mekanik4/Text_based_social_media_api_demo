package com.demo.text_based_social_media.api.follow.adapter.in;

import com.demo.text_based_social_media.api.follow.adapter.in.dto.FollowDto;
import com.demo.text_based_social_media.api.follow.application.port.in.SaveFollowUseCase;
import com.demo.text_based_social_media.config.security.services.UserDetailsImpl;
import com.demo.text_based_social_media.mapper.FollowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FollowRestController {

    private final SaveFollowUseCase saveFollowUseCase;

    private final FollowMapper followMapper;

    @PostMapping(path = "/users/me/follow")
    public void addFollow(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody FollowDto followDto) {
        Long userId = userDetails.getId();
        saveFollowUseCase.saveFollow(followDto, userId);
    }

    @DeleteMapping(path = "users/me/followers/delete")
    public void deleteFollower(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody FollowDto followDto) {
        Long userId = userDetails.getId();
        saveFollowUseCase.deleteFollow(followDto, userId);
    }
}
