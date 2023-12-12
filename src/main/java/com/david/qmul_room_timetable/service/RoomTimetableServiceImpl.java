package com.david.qmul_room_timetable.service;

import com.david.qmul_room_timetable.FetchRoomTimetableTask;
import com.david.qmul_room_timetable.dto.RoomTimetableQuery;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RoomTimetableServiceImpl implements RoomTimetableService {

    @Override
    public String getRoomTimetable(RoomTimetableQuery[] roomTimetableQueries) throws InterruptedException {
        int nThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        List<FetchRoomTimetableTask> tasks = Arrays.stream(roomTimetableQueries)
                .map(FetchRoomTimetableTask::new)
                .toList();
        tasks.forEach(executorService::execute);
        executorService.awaitTermination(30,  TimeUnit.SECONDS);


        return tasks.stream()
                .map(task -> task.getRoomTimetable())
                .collect(Collectors.joining());
    }

}
