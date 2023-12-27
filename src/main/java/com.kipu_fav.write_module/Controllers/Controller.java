package com.kipu_fav.write_module.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.kipu_fav.write_module.Entity.Booking;
import com.kipu_fav.write_module.Entity.Schedule;
import com.kipu_fav.write_module.SecuredLoggerSanitizer;
import com.kipu_fav.write_module.Service.Schedule_service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/schedule")
public class Controller {
    @Autowired
    Schedule_service schedule_service;

    @PostMapping("/new")
    public String createSchedule(@RequestBody Schedule schedule){
        log.info("schedule created" + schedule);
        this.schedule_service.saveSchedule(schedule);
        return "schedule created!!!";
    }

    @GetMapping("/all")
    public List<Schedule> getAllSchedule(){

          // BAD: user password is written to debug log
        log.info("schedule fetched" + this.schedule_service.getAllSchedule());
        log.info("schedule fetched 0" + this.schedule_service.getAllSchedule().get(0));
        return this.schedule_service.getAllSchedule();
    }

    @PostMapping("/book")
    public String book(@RequestBody Booking booking){
        log.info("booking created" + booking);
        this.schedule_service.book(booking);
        log.info("booking success" + booking);


        log.info("booking created again again again again again again again" + booking);
        return "Booking Success";
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateSchedule(@RequestBody String scheduleID){

        // BAD: user password is written to debug log

        log.info("schedule updated" + scheduleID);
        log.info("schedule updated again" + scheduleID);
        log.info("schedule updated again again" + scheduleID);
        log.info("schedule updated again again again" + scheduleID);
        return ResponseEntity.status(HttpStatus.CREATED).body("Post end point working."+ SecuredLoggerSanitizer.sanitize(scheduleID));

    }



    @GetMapping(path = "/patient/occurrences")
    public ResponseEntity<List<JsonNode>> getAllOccurrences(Schedule searchParam) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedule_service.getAllOccurrences( searchParam ));
    }
}
