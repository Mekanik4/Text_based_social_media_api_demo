package com.demo.text_based_social_media.api.comment.adapter.out;

import com.demo.text_based_social_media.api.comment.application.port.out.CommentReadPort;
import com.demo.text_based_social_media.api.comment.application.port.out.CommentSavePort;
import com.demo.text_based_social_media.api.comment.domain.Comment;
import com.demo.text_based_social_media.mapper.CommentMapper;
import com.demo.text_based_social_media.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Transactional
public class CommentAdapter implements CommentReadPort, CommentSavePort {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Override
    public void save(final Comment comment) {
        commentRepository.save(commentMapper.fromDomain(comment));
    }

    @Override
    public int countCommentsByUserIdAndPostId(Long userId, Long postId) {
        return commentRepository.countByUserIdAndPostId(userId, postId);
    }
}
