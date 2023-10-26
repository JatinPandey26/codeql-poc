package com.kipu_fav.write_module.Repository;

import com.kipu_fav.write_module.Entity.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Schedule_repository extends MongoRepository<Schedule,Long> {

}
