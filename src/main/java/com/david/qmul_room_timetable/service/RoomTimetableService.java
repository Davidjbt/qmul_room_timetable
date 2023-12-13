package com.david.qmul_room_timetable.service;

import com.david.qmul_room_timetable.dto.RoomTimetableQuery;

public interface RoomTimetableService {

    String[] getRoomTimetable(RoomTimetableQuery[] roomTimetableQuery) throws InterruptedException;

}
