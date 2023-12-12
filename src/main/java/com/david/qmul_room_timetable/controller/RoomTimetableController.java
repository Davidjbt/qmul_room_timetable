package com.david.qmul_room_timetable.controller;

import com.david.qmul_room_timetable.dto.RoomTimetableQuery;
import com.david.qmul_room_timetable.service.RoomTimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomTimetableController {

    private final RoomTimetableService roomTimetableService;

    @PostMapping("/timetable")
    public String getRoomTimetables(RoomTimetableQuery[] searchQueries) {
        return roomTimetableService.getRoomTimetable(searchQueries);
    }
}
