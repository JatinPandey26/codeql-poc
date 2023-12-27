package com.kipu_fav.write_module.Entity;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Document(collection = "schedule")
public class Schedule {

    private String resource_name;
    private String location_name;
    private LocalDate start_date;
    private LocalDate end_date;
    private LocalTime start_time;
    private LocalTime end_time;
    private List<String> off_days;
    private List<List<String>> off_times;

    public Object getPatientId() {
        return null;
    }

    public Boolean getSession() {
        return null;
    }

    public List<Integer> getLocationIds() {
        return new ArrayList<>();
    }
}
