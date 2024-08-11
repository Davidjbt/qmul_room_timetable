package com.david.qmul_room_timetable.dto;

import java.util.Map;

public record QueryResult(
        String day,
        String resultHtml,
        Map<String, String> resultStyling
) {
}
