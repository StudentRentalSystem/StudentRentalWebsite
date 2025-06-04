package xyz.jessyu.studentrentalwebsite.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.jessyu.studentrentalwebsite.model.OAuth2UserEntity;

@Repository
public interface OAuth2UserRepository extends MongoRepository<OAuth2UserEntity, String> {
    OAuth2UserEntity findByEmail(String email);
}
