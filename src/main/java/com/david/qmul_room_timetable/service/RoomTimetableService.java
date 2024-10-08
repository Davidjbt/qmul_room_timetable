package com.david.qmul_room_timetable.service;

import com.david.qmul_room_timetable.dto.Campus;
import com.david.qmul_room_timetable.dto.QueryResult;
import com.david.qmul_room_timetable.dto.RoomTimetableQuery;

public interface RoomTimetableService {

    QueryResult[] getRoomTimetable(RoomTimetableQuery[] roomTimetableQuery) throws InterruptedException;

    Campus[] getAllRooms() throws InterruptedException;
}
