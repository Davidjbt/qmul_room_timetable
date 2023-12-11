package com.david.qmul_room_timetable.dto;

import lombok.Data;

@Data
public class RoomTimetableSearchQuery {

    private String building;
    private String[] rooms;
    private String week;
    private String day;

}
