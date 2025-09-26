package com.spark.dating.chat.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.spark.dating.dto.chat.ChatMessageSelectRequest;
import com.spark.dating.dto.chat.ChatRoomCreateRequest;
import com.spark.dating.dto.chat.ChatRoomSelectRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "채팅 컨트롤러", description = "채팅 관련해서 처리하는 컨트롤러입니다.")
public interface ChatControllerDocs {

	@Operation(summary = "채팅방 생성", description = "매칭을 수락하면 machingNo를 받아 채팅방을 만듭니다.", parameters = {
			@Parameter(name = "matchingNo", description = "완료된 매칭에 대해 채팅방 생성을 위한 변수") })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "유저 정보 저장 성공"),
			@ApiResponse(responseCode = "CH001", description = "유저 정보 저장 실패(유저 중복)"),
			@ApiResponse(responseCode = "CH002", description = "유저 정보 저장 실패(유저 중복)") })
	public void createChatRoom(@Valid @RequestBody ChatRoomCreateRequest ChatRoomCreateRequest);

	@Operation(summary = "채팅방 조회", description = "로그인한 사용자의 m_id를 통해 채팅방 리스트를 조회합니다.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "유저 정보 저장 성공"),
			@ApiResponse(responseCode = "409", description = "유저 정보 저장 실패(유저 중복)") })
	public List<ChatRoomSelectRequest> chatRoomList(@RequestParam("m_id") final int userNo);

	@Operation(summary = "채팅방 메세지 조회", description = "클라이언트가 요청한 rooId를 통해 해당 방에 있는 메세지들을 조회합니다.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "유저 정보 저장 성공"),
			@ApiResponse(responseCode = "409", description = "유저 정보 저장 실패(유저 중복)") })
	public List<ChatMessageSelectRequest> chatMessage(@PathVariable("roomId") final int roomId);

}
