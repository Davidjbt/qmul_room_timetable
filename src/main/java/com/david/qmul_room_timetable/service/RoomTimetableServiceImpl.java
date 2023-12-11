package com.david.qmul_room_timetable.service;

import com.david.qmul_room_timetable.dto.RoomTimetableSearchQuery;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class RoomTimetableServiceImpl implements RoomTimetableService {

    @Override
    public String getRoomTimetable(RoomTimetableSearchQuery[] roomTimetableSearchQuery) {
        int nThreads = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(nThreads);

        Arrays.stream(roomTimetableSearchQuery).forEach(query -> pool.execute());


        return null;
    }

}
