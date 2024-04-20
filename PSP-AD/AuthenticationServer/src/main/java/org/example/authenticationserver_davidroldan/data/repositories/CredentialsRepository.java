package org.example.authenticationserver_davidroldan.data.repositories;

import org.example.authenticationserver_davidroldan.data.modelo.CredentialsEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialsRepository extends
        ListCrudRepository<CredentialsEntity, Integer> {

    CredentialsEntity findByUsername(String username);
}
