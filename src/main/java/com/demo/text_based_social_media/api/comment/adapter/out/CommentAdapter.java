package com.demo.text_based_social_media.api.comment.adapter.out;

import com.demo.text_based_social_media.api.comment.application.port.out.CommentReadPort;
import com.demo.text_based_social_media.api.comment.application.port.out.CommentSavePort;
import com.demo.text_based_social_media.api.comment.domain.Comment;
import com.demo.text_based_social_media.api.user.authentication.application.port.out.ReadUserPort;
import com.demo.text_based_social_media.mapper.CommentMapper;
import com.demo.text_based_social_media.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
@Transactional
public class CommentAdapter implements CommentReadPort, CommentSavePort {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final ReadUserPort readUserPort;

    @Override
    public void save(final Comment comment) {
        commentRepository.save(commentMapper.fromDomain(comment));
    }

    @Override
    public int countCommentsByUserIdAndPostId(Long userId, Long postId) {
        return commentRepository.countByUserIdAndPostId(userId, postId);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId).stream()
                .map(commentMapper::toDomain)
                .peek(comment -> comment.setUser(readUserPort.readUserById(comment.getUserId())))
                .collect(Collectors.toList());
    }

    @Override
    public Comment getLatestCommentByUserIdAndPostId(Long userId, Long postId) {
        return commentMapper.toDomain(commentRepository.findLatestCommentByUserIdAndPostId(userId, postId));
    }
}
