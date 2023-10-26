package com.kipu_fav.write_module.Repository;

import com.kipu_fav.write_module.Entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Booking_Repository extends MongoRepository<Booking,Long> {
}
