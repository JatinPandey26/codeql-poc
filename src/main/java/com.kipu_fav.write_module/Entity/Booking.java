package com.kipu_fav.write_module.Entity;

import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Document(collection = "booking")
public class Booking {
    private String id_redis;
    private String resource_name;
    private String location_name;
    private String date;
    private String start_time;
    private String end_time;
    private long duration;
}
