// Copyright (c) 2018 Travelex Ltd

package info.niteshjha.service;

import info.niteshjha.exception.UserNotFoundException;
import info.niteshjha.model.UserCreate;
import info.niteshjha.repository.UserCreateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class UserCreateService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private UserCreateRepository userCreateRepository;

    public UserCreateService(UserCreateRepository userCreateRepository) {
        this.userCreateRepository = userCreateRepository;
    }


    public List<UserCreate> getUserList() {
        logger.info("Fetching the list of users from database");
        return StreamSupport.stream(this.userCreateRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserCreate getCreatedUser(Long userId) {
        logger.info("Fetching user with id :: " + userId);
        return this.userCreateRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userIdToDelete) {
        logger.info("Deleting user with id :: " + userIdToDelete);
        this.userCreateRepository.deleteById(userIdToDelete);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserCreate modifyUser(UserCreate userModify) {
        logger.info("Modifying user with email :: " + userModify.getEmail());
        userModify.setDateModified(new Date());
        return this.userCreateRepository.save(userModify);
    }


    @Transactional(rollbackFor = Exception.class)
    public UserCreate createUser(UserCreate userCreate) {
        logger.info("Creating user with email :: " + userCreate.getEmail());
        userCreate.setDateCreated(new Date());
        return this.userCreateRepository.save(userCreate);
    }
}
