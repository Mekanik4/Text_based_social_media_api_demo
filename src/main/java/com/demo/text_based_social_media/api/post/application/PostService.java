package com.demo.text_based_social_media.api.post.application;

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

@RequiredArgsConstructor
@Service
@Transactional
public class PostService implements ReadPostUseCase, SavePostUseCase {

    private final PostReadPort postReadPort;

    private final PostSavePort postSavePort;

    private final ReadUserPort readUserPort;
    @Override
    public void savePost(Post post) {
        post.setPostedAt(LocalDateTime.now());
        postSavePort.save(post);
    }

    @Override
    public Post getPostById(Long id) {
        return postReadPort.getPostById(id);
    }
}
