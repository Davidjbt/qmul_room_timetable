package com.david.qmul_room_timetable.service;

import com.david.qmul_room_timetable.dto.RoomTimetableQuery;
import org.springframework.stereotype.Service;

@Service
public interface RoomTimetableService {

    public String getRoomTimetable(RoomTimetableQuery[] roomTimetableQuery) throws InterruptedException;

}
