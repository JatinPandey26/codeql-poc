package com.kipu_fav.write_module.Service;

import com.kipu_fav.write_module.Entity.Booking;
import com.kipu_fav.write_module.Entity.Schedule;
import com.kipu_fav.write_module.Repository.Booking_Repository;
import com.kipu_fav.write_module.Repository.Schedule_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Schedule_service {

    @Autowired
    Schedule_repository scheduleRepository;

    @Autowired
    Booking_Repository bookingRepository;

    @Autowired
    KafkaTemplate<String,Booking> bookingKafkaTemplate;

    private final String BOOKING_KAFKA_TOPIC = "booking-kafka-topic";

    public void saveSchedule(Schedule schedule){
        this.scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedule(){
        return this.scheduleRepository.findAll();
    }

    public void book(Booking booking){
        this.bookingRepository.save(booking);

        this.bookingKafkaTemplate.send(BOOKING_KAFKA_TOPIC,booking);

    }
}
