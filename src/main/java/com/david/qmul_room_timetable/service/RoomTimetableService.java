package com.david.qmul_room_timetable.service;

import com.david.qmul_room_timetable.dto.RoomTimetableSearchQuery;
import org.springframework.stereotype.Service;

@Service
public interface RoomTimetableService {

    public String getRoomTimetable(RoomTimetableSearchQuery[] roomTimetableSearchQuery);

}
