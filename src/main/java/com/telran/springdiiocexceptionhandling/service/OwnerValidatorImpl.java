package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.repository.TopicRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.IllegalIdException;
import org.springframework.stereotype.Component;


@Component
public class OwnerValidatorImpl implements OwnerValidator {
    private TopicRepository repository;

    public OwnerValidatorImpl(TopicRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean topicOwnerValidator(String topicId, String owner) {

        if (!repository.getTopicById(Integer.valueOf(topicId)).getOwner().equals(owner)) {
            throw new SecurityException("Wrong topic owner" + owner);
        }
        return true;
    }

    @Override
    public boolean commentOwnerValidator(String topicId, String commentId, String owner) {
        try {
            CommentEntity entity = repository.getCommentById(Integer.valueOf(topicId), Integer.valueOf(commentId));
            if (!entity.getOwner().equals(owner)) {
                throw new SecurityException("Wrong comment owner: " + owner);
            }
            return true;
        } catch (IllegalIdException ex) {
            throw new SecurityException(ex.getMessage(), ex);
        }
    }

}
