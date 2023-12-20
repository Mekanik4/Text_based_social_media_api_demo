package com.demo.text_based_social_media.api.post.adapter.out;

import com.demo.text_based_social_media.api.post.application.port.out.PostReadPort;
import com.demo.text_based_social_media.api.post.application.port.out.PostSavePort;
import com.demo.text_based_social_media.api.post.domain.Post;
import com.demo.text_based_social_media.mapper.PostMapper;
import com.demo.text_based_social_media.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Transactional
public class PostAdapter implements PostReadPort, PostSavePort {

    private final PostRepository postRepository;

    private final PostMapper postMapper;
    @Override
    public Post getPostById(Long id) {
        return postMapper.toDomain(postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find post with id " + id)));
    }

    @Override
    public void save(final Post post) {
        postRepository.save(postMapper.entityFromDomain(post));
    }

}
