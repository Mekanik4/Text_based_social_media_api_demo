package com.demo.text_based_social_media.api.post.adapter.out;

import com.demo.text_based_social_media.api.post.application.port.out.PostReadPort;
import com.demo.text_based_social_media.api.post.application.port.out.PostSavePort;
import com.demo.text_based_social_media.api.post.domain.Post;
import com.demo.text_based_social_media.mapper.PostMapper;
import com.demo.text_based_social_media.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<Post> getAllPostsByUserId(Long userId) {
        return postRepository.findAllByUserIdOrderByPostedAtDesc(userId).stream()
                .map(postMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> getAllFollowingPosts(List<Long> userIds) {
        return postRepository.findAllByUserIdInOrderByPostedAtDesc(userIds).stream()
                .map(postMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getPostIdsByUserId(Long userId) {
        return postRepository.findPostIdsByUserId(userId);
    }

    @Override
    public List<Long> getPostIdsByFollowingIds(List<Long> followingIds) {
        return postRepository.findPostIdsByFollowingIds(followingIds);
    }

    @Override
    public void save(final Post post) {
        postRepository.save(postMapper.entityFromDomain(post));
    }

}
