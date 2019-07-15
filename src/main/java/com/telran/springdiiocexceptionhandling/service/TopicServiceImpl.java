package com.telran.springdiiocexceptionhandling.service;

import com.telran.springdiiocexceptionhandling.controllers.dto.CommentFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicFullDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.TopicResponseDto;
import com.telran.springdiiocexceptionhandling.repository.TopicRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.entity.RolesEntity;
import com.telran.springdiiocexceptionhandling.repository.entity.TopicEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.IllegalIdException;
import com.telran.springdiiocexceptionhandling.repository.exception.RepositoryException;
import com.telran.springdiiocexceptionhandling.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TopicServiceImpl implements TopicService {
    @Autowired
    TopicRepository topicRepository;

    @Autowired
    OwnerValidator validator;

    @Override
    public void addTopic(TopicResponseDto topicResponseDto) {
        try {
            topicRepository.addTopic(map(topicResponseDto));
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(),ex);
        }
    }

    @Override
    public Iterable<TopicFullDto> getAllTopics() {
        return StreamSupport.stream(topicRepository.getAllTopics().spliterator(), false)
                .map(this::map)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void removeById(String id) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = principal.getName();
        //TODO need to check roles
        try {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains("ROLE_"+ RolesEntity.Role.ADMIN)) {
                topicRepository.removeTopic(UUID.fromString(id));
                return;
            }
            if (validator.topicOwnerValidator(id, userEmail)) {
                topicRepository.removeTopic(UUID.fromString(id));
            }
        } catch (IllegalIdException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        } catch (RepositoryException ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }

    }

    private TopicEntity map(TopicResponseDto dto) {
        return new TopicEntity(
                UUID.fromString(dto.getId()),
                dto.getAuthor(),
                dto.getTitle(),
                dto.getContent(),
                dto.getDate(),
                null
        );
    }

    private TopicFullDto map(TopicEntity topicEntity){
        return TopicFullDto.fullTopicBuilder()
                .id(topicEntity.getId().toString())
                .author(topicEntity.getAuthor())
                .title(topicEntity.getTitle())
                .content(topicEntity.getContent())
                .comments(map(topicEntity.getComments()))
                .date(topicEntity.getDate())
                .build();
    }

    private List<CommentFullDto> map(List<CommentEntity> list){
        return list.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private CommentFullDto map(CommentEntity entity){
        return CommentFullDto.fullCommentBuilder()
                .id(entity.getId().toString())
                .date(entity.getDate())
                .author(entity.getAuthor())
                .message(entity.getMessage())
                .build();
    }
}
