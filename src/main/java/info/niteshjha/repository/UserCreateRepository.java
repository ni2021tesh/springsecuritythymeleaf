// Copyright (c) 2018 Travelex Ltd

package info.niteshjha.repository;

import info.niteshjha.model.UserCreate;
import org.springframework.data.repository.CrudRepository;

public interface UserCreateRepository extends CrudRepository<UserCreate,Long> {
}
