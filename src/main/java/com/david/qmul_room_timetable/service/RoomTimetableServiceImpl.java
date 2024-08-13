package com.david.qmul_room_timetable.service;

import com.david.qmul_room_timetable.dto.Campus;
import com.david.qmul_room_timetable.dto.QueryResult;
import com.david.qmul_room_timetable.dto.RoomTimetableQuery;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class RoomTimetableServiceImpl implements RoomTimetableService {

    @Override
    public QueryResult[] getRoomTimetable(RoomTimetableQuery[] roomTimetableQueries) throws InterruptedException {
        int nThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        List<FetchRoomTimetableTask> tasks = Arrays.stream(roomTimetableQueries)
                .map(FetchRoomTimetableTask::new)
                .toList();

        tasks.forEach(executorService::execute);
        executorService.shutdown();
        executorService.awaitTermination(30,  TimeUnit.SECONDS);

        return tasks.stream()
                .map(FetchRoomTimetableTask::getQueryResult)
                .toList().toArray(new QueryResult[0]);
    }

    @Override
    public Campus[] getAllRooms() throws InterruptedException {
        return new GetAllRoomsTask().getAllRooms();
    }
}
