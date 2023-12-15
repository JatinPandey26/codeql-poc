package com.kipu_fav.write_module.Service;

import com.kipu_fav.write_module.Entity.Booking;
import com.kipu_fav.write_module.Entity.Schedule;
import com.kipu_fav.write_module.Repository.Booking_Repository;
import com.kipu_fav.write_module.Repository.Schedule_repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class Schedule_service {

    @Autowired
    Schedule_repository scheduleRepository;

    @Autowired
    Booking_Repository bookingRepository;

    @Autowired
    KafkaTemplate<String,Booking> bookingKafkaTemplate;

    private final String BOOKING_KAFKA_TOPIC = "booking-kafka-topic";

    public void saveSchedule(Schedule schedule){


        log.info("schedule created" + schedule + " " + schedule.getLocation_name() + " " + schedule.getResource_name());
        this.scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedule(){

        log.info("schedule fetched" + this.scheduleRepository.findAll());
        return this.scheduleRepository.findAll();
    }

    public void book(Booking booking){
        this.bookingRepository.save(booking);
        log.info("booking created" + booking);
        log.info("booking created" + booking.getResource_name());
        this.bookingKafkaTemplate.send(BOOKING_KAFKA_TOPIC,booking);

    }
}
