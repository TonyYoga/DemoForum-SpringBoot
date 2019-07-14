package com.telran.springdiiocexceptionhandling.service;

public interface OwnerValidator {

    boolean topicOwnerValidator(String topicId, String ownerEmail);
    boolean commentOwnerValidator(String topicId, String commentId, String ownerEmail);
}
