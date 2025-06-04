package xyz.jessyu.studentrentalwebsite.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.jessyu.studentrentalwebsite.model.RentalInfo;

import java.util.List;

@Repository
public interface RentalInfoRepository extends MongoRepository<RentalInfo, String> {

    // test
    List<RentalInfo> findByAddressContaining(String keyword);
    List<RentalInfo> findByAllowPet(int allowPet);
}