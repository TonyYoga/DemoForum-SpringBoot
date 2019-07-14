package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.repository.TopicRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.IllegalIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OwnerValidatorImpl implements OwnerValidator {
    @Autowired
    TopicRepository repository;

    @Override
    public boolean topicOwnerValidator(String topicId, String owner) {

        if (!repository.getTopicById(UUID.fromString(topicId)).getAuthor().equals(owner)) {
            throw new SecurityException("Wrong topic owner" + owner);
        }
        return true;
    }

    @Override
    public boolean commentOwnerValidator(String topicId, String commentId, String owner) {
        try {
            CommentEntity entity = repository.getCommentById(UUID.fromString(topicId), UUID.fromString(commentId));
            if (entity.getAuthor() != owner) {
                throw new SecurityException("Wrong comment owner" + owner);
            }
            return true;
        } catch (IllegalIdException ex) {
            throw new SecurityException(ex.getMessage(), ex);
        }
    }
}
