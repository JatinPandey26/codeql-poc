package com.kipu_fav.write_module.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.kipu_fav.write_module.Entity.Booking;
import com.kipu_fav.write_module.Entity.Schedule;
import com.kipu_fav.write_module.GenericBuilder;
import com.kipu_fav.write_module.Repository.Booking_Repository;
import com.kipu_fav.write_module.Repository.Schedule_repository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jpa.QueryHints;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class Schedule_service {

    @Autowired
    Schedule_repository scheduleRepository;

    @Autowired
    Booking_Repository bookingRepository;

    @Autowired
    KafkaTemplate<String,Booking> bookingKafkaTemplate;

    @Autowired
    EntityManager entityManager;

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
        String sql = "SELECT * FROM schedule WHERE resource_name = '" + booking.getResource_name() + "'";
        List<Schedule> scheduleList = this.entityManager.createNativeQuery(sql, Schedule.class).getResultList();
       log.info("schedule fetched" + scheduleList);
        this.bookingKafkaTemplate.send(BOOKING_KAFKA_TOPIC,booking);

    }

    public List<JsonNode> getAllOccurrences(Schedule searchParam) {
        log.info(" Patient Occ call With param : {}", searchParam.toString());
        if (Objects.isNull(searchParam.getPatientId()))
            return new ArrayList<>();
        StringBuilder query = new StringBuilder(" WITH GROUP_LEADERS_DATA AS (" +
                " SELECT GS_GROUP_SESSION_ID,string_agg(USERS.FULL_NAME,',') AS LEADER_DETAILS " +
                " FROM GROUP_LEADERS AS GL" +
                " INNER JOIN USERS ON USERS.ID = GL.USER_ID " +
                " GROUP BY GS_GROUP_SESSION_ID " +
                " ) SELECT  S.name  as schedule_name ," +
                " CAST(GSPS.START_TIME AT TIME ZONE 'UTC' AT Time Zone TZN.tzinfo_name AS DATE) as start_date , " +
                " CAST(CONCAT(GSO.start_hour,':',GSO.start_minute) AS time) as start_time , " +
                " GSO.week_day as days , " +
                " JSON_BUILD_OBJECT('schedule_name',S.name,'session_name',GS.title,'day', to_char(date_trunc('week', current_date) + INTERVAL '1 day' * (GSO.week_day-1), 'Dy')," +
                " 'start_date',CAST(GSPS.START_TIME AT TIME ZONE 'UTC' AT Time Zone TZN.tzinfo_name AS DATE)," +
                " 'end_date',CAST(GSPS.END_TIME AT TIME ZONE 'UTC' AT Time Zone TZN.tzinfo_name AS DATE)," +
                "   'start_time',CAST(CONCAT(GSO.start_hour,':',GSO.start_minute) AS time)," +
                "   'duration',case when GSO.start_hour*60 + GSO.start_minute < GSO.end_hour*60 + GSO.end_minute  " +
                "  then  GSO.end_hour*60 + GSO.end_minute - GSO.start_hour*60 + GSO.start_minute" +
                "  else GSO.end_hour*60 + GSO.end_minute - GSO.start_hour*60 + GSO.start_minute + 24 end," +
                " 'telehealth',COALESCE(GSO.is_telehealth,false),'resource',COALESCE(GLD.LEADER_DETAILS,'')) AS JSON_DATA " +
                " FROM GROUP_SESSION_PATIENT_SUBSCRIPTION GSPS " +
                " JOIN GS_GROUP_SESSION_OCCURRENCES as GSO on GSO.id = GSPS.GS_OCCURRENCE_ID" +
                " JOIN GS_GROUP_SESSIONS AS GS ON GS.ID = GSO.group_session_id" +
                " JOIN LOCATIONS AS l ON GSPS.location_id = l.id" +
                " JOIN TIMEZONE_TZINFO_NAMES AS TZN ON TZN.timezone_name = l.time_zone" +
                " LEFT JOIN ECAL_PATIENT_SCHEDULES EPS ON GSPS.ECAL_PATIENT_SCHEDULE_ID = EPS.ID" +
                " LEFT JOIN SCHEDULES S ON  EPS.SCHEDULE_ID = S.ID" +
                " LEFT JOIN GROUP_LEADERS_DATA AS GLD ON GLD.GS_GROUP_SESSION_ID = GS.ID" +
                " WHERE GSPS.END_TIME > NOW() AT TIME ZONE 'UTC'" +
                " AND COALESCE(GSPS.DELETED,false) = false" +
                " AND COALESCE(S.ENABLED,TRUE) IS TRUE" +
                " AND COALESCE(S.DELETED,FALSE) IS FALSE " +
                " AND GSPS.PATIENT_ID = :patientId ");

        if (!CollectionUtils.isEmpty(searchParam.getLocationIds())) {
            query.append(" AND GSPS.LOCATION_ID in ").append(GenericBuilder.arrayAppender(searchParam.getLocationIds()));
        }
        if (Boolean.FALSE.equals(searchParam.getSession()) || Objects.isNull(searchParam.getSession())) {
            query.append(" AND GSPS.ecal_patient_schedule_id IS not NULL");
        }
        query.append(" GROUP BY GSO.ID,GSPS.START_TIME,GSPS.END_TIME,TZN.tzinfo_name,GS.ID,GLD.LEADER_DETAILS,S.ID");
        if (Boolean.TRUE.equals(searchParam.getSession())) {
            query.append(" ORDER BY start_date,days,start_time ");
        } else {
            query.append(" ORDER BY schedule_name,start_date,days,start_time ");
        }
        List<JsonNode> patientOccurrence = entityManager.createNativeQuery(query.toString()).
                unwrap(NativeQuery.class).setHint(QueryHints.HINT_READONLY, true)
                .setParameter("patientId", searchParam.getPatientId()).getResultList();
        entityManager.clear();
        return patientOccurrence;
    }
}
