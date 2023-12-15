package com.kipu_fav.write_module.Controllers;

import com.kipu_fav.write_module.Entity.Booking;
import com.kipu_fav.write_module.Entity.Schedule;
import com.kipu_fav.write_module.Service.Schedule_service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        return this.schedule_service.getAllSchedule();
    }

    @PostMapping("/book")
    public String book(@RequestBody Booking booking){
        this.schedule_service.book(booking);
        return "Booking Success";
    }
}
