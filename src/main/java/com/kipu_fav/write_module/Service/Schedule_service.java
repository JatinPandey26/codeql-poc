package com.kipu_fav.write_module.Service;

import com.kipu_fav.write_module.Entity.Schedule;
import com.kipu_fav.write_module.Repository.Schedule_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Schedule_service {

    @Autowired
    Schedule_repository scheduleRepository;

    public void saveSchedule(Schedule schedule){
        this.scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedule(){
        return this.scheduleRepository.findAll();
    }

}
