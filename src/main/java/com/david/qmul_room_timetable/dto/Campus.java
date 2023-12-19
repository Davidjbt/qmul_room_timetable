package com.david.qmul_room_timetable.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Campus {

    private final String campus;
    private List<Building> buildings;

    @Data
    @RequiredArgsConstructor
    public static class Building {

        private final String building;
        private final String[] rooms;
    }

}

