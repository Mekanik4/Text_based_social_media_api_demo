package com.demo.text_based_social_media.api.follow.adapter.in;

import com.demo.text_based_social_media.api.follow.adapter.in.dto.FollowDto;
import com.demo.text_based_social_media.api.follow.adapter.in.dto.FollowViewDto;
import com.demo.text_based_social_media.api.follow.application.port.in.ReadFollowUseCase;
import com.demo.text_based_social_media.api.follow.application.port.in.SaveFollowUseCase;
import com.demo.text_based_social_media.config.security.services.UserDetailsImpl;
import com.demo.text_based_social_media.mapper.FollowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class FollowRestController {

    private final SaveFollowUseCase saveFollowUseCase;

    private final ReadFollowUseCase readFollowUseCase;

    private final FollowMapper followMapper;

    @PostMapping(path = "/users/me/follow")
    public ResponseEntity<String> addFollow(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody FollowDto followDto) {
        Long userId = userDetails.getId();
        saveFollowUseCase.saveFollow(followDto, userId);
        return ResponseEntity.status(HttpStatus.OK).body("You started following " + followDto.getFollowingName());
    }

    @DeleteMapping(path = "/users/me/followers/delete")
    public ResponseEntity<String> deleteFollower(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody FollowDto followDto) {
        Long userId = userDetails.getId();
        saveFollowUseCase.deleteFollow(followDto, userId);
        return ResponseEntity.status(HttpStatus.OK).body("You unfollowed " + followDto.getFollowingName());
    }

    @GetMapping(path = "/users/me/following")
    public List<FollowViewDto> getAllFollowing(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        return readFollowUseCase.getAllByFollowerId(userId).stream()
                .map(followMapper::followingDtoFromDomain)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/users/me/followers")
    public List<FollowViewDto> getAllFollowers(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        return readFollowUseCase.getAllByFollowingId(userId).stream()
                .map(followMapper::followerDtoFromDomain)
                .collect(Collectors.toList());
    }
}
