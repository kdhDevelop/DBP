package kr.ac.kmu.dbp.dto.chat.chatRoom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ChatRoomDtoCreate {
    private String name;
}
