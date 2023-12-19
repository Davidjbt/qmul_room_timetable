package com.david.qmul_room_timetable.controller;

import com.david.qmul_room_timetable.dto.Campus;
import com.david.qmul_room_timetable.dto.RoomTimetableQuery;
import com.david.qmul_room_timetable.service.RoomTimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomTimetableController {

    private final RoomTimetableService roomTimetableService;

    @PostMapping("/timetable")
    public String[] getRoomTimetables(@RequestBody RoomTimetableQuery[] searchQueries) throws InterruptedException {
        return roomTimetableService.getRoomTimetable(searchQueries);
    }

    @GetMapping("/getAll")
    public Campus[] getAllRooms() throws InterruptedException {
        return roomTimetableService.getAllRooms();
    }

}
