package com.demo.text_based_social_media.api.post.application;

import com.demo.text_based_social_media.api.comment.application.port.out.CommentReadPort;
import com.demo.text_based_social_media.api.follow.application.port.out.FollowReadPort;
import com.demo.text_based_social_media.api.follow.domain.Follow;
import com.demo.text_based_social_media.api.post.application.port.in.ReadPostUseCase;
import com.demo.text_based_social_media.api.post.application.port.in.SavePostUseCase;
import com.demo.text_based_social_media.api.post.application.port.out.PostReadPort;
import com.demo.text_based_social_media.api.post.application.port.out.PostSavePort;
import com.demo.text_based_social_media.api.post.domain.Post;
import com.demo.text_based_social_media.api.user.authentication.application.port.out.ReadUserPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService implements ReadPostUseCase, SavePostUseCase {

    private final PostReadPort postReadPort;

    private final PostSavePort postSavePort;

    private final ReadUserPort readUserPort;

    private final FollowReadPort followReadPort;

    private final CommentReadPort commentReadPort;

    @Override
    public void savePost(Post post) {
        post.setPostedAt(LocalDateTime.now());
        postSavePort.save(post);
    }

    @Override
    public Post getPostById(Long id) {
        return postReadPort.getPostById(id);
    }

    @Override
    public List<Post> getAllPostsByUserId(Long userId) {
        return postReadPort.getAllPostsByUserId(userId).stream()
                .peek(post -> {
                    post.setUser(readUserPort.readUserById(userId));
                    post.setComments(commentReadPort.getCommentsByPostId(post.getId()));
                }).toList();
    }

    @Override
    public List<Post> getAllFollowingPost(Long userId) {
        List<Long> userIds = followReadPort.getAllByFollowerId(userId).stream()
                .map(Follow::getFollowingId)
                .toList();
        return postReadPort.getAllFollowingPosts(userIds).stream()
                .peek(post -> post.setUser(readUserPort.readUserById(post.getUserId())))
                .toList();
    }
}
