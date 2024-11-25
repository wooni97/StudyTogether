package dev.flab.studytogether.api.controller;

import dev.flab.studytogether.common.aop.PostMethodLog;
import dev.flab.studytogether.domain.room.dto.RoomCreateRequest;
import dev.flab.studytogether.domain.room.dto.StudyRoomResponse;
import dev.flab.studytogether.core.domain.room.entity.ParticipantRole;
import dev.flab.studytogether.core.domain.room.entity.StudyRoom;
import dev.flab.studytogether.domain.room.service.StudyRoomExitService;
import dev.flab.studytogether.domain.room.service.StudyRoomService;
import dev.flab.studytogether.common.utils.SessionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@AllArgsConstructor
@Tag(name = "Study Rooom", description = "스터디룸 API")
public class StudyRoomApiController {

    private final StudyRoomService studyRoomService;
    private final StudyRoomExitService studyRoomExitService;

    @PostMapping("/api/v1/rooms")
    @PostMethodLog
    @Operation(summary = "Create room", description = "스터디룸 생성")
    @ResponseStatus(HttpStatus.OK)
    public StudyRoomResponse createRoom(@RequestBody RoomCreateRequest requestDto, HttpSession httpSession) {
        int memberSequenceId = getMemberSequenceId(httpSession);

        StudyRoom studyRoom = studyRoomService.createRoom(requestDto.getRoomName(),
                requestDto.getMaxParticipants(),
                memberSequenceId);

        return StudyRoomResponse.from(studyRoom);
    }
    @GetMapping("/api/v1/rooms")
    @Operation(summary = "Enter Room", description = "스터디룸 입장")
    @ResponseStatus(HttpStatus.OK)
    public StudyRoomResponse enterRoom(@RequestParam(name = "roomId") int roomId,
                                       HttpSession httpSession){
        int memberSeqId = getMemberSequenceId(httpSession);
        StudyRoom studyRoom = studyRoomService.enterRoom(roomId, memberSeqId, ParticipantRole.ORDINARY_PARTICIPANT);

        return StudyRoomResponse.from(studyRoom);
    }

    @DeleteMapping("/api/v1/rooms")
    @Operation(summary = "Exit Room", description = "스터디룸 퇴장")
    @ResponseStatus(HttpStatus.OK)
    public StudyRoomResponse exitRoom(@RequestParam(name = "roomId") int roomId,
                                      HttpSession httpSession){
        int memberSeqId = getMemberSequenceId(httpSession);
        StudyRoom studyRoom = studyRoomExitService.exitRoom(roomId, memberSeqId);

        return StudyRoomResponse.from(studyRoom);
    }


    @GetMapping("/api/v1/rooms/activated")
    @ResponseStatus(HttpStatus.OK)
    public List<StudyRoomResponse> getActivatedStudyRooms() {
        List<StudyRoom> studyRooms = studyRoomService.getActivatedStudyRooms();

        return studyRooms.stream()
                .map(StudyRoomResponse::from)
                .toList();
    }

    @GetMapping("/api/v1/rooms/enter-available")
    @ResponseStatus(HttpStatus.OK)
    public List<StudyRoomResponse> getEnterAvailableStudyRooms() {
        List<StudyRoom> studyRooms = studyRoomService.getEnterAvailableStudyRooms();

        return studyRooms.stream()
                .map(StudyRoomResponse::from)
                .toList();
    }

    @GetMapping("/api/v1/rooms/info")
    @ResponseStatus(HttpStatus.OK)
    public StudyRoomResponse getRoomInfo(@RequestParam(name = "roomId") int roomId) {
        StudyRoom studyRoom = studyRoomService.getRoomInformation(roomId);
        return StudyRoomResponse.from(studyRoom);
    }


    private int getMemberSequenceId(HttpSession httpSession) {
        return SessionUtil.getLoginMemebrSeqId(httpSession);
    }

}
