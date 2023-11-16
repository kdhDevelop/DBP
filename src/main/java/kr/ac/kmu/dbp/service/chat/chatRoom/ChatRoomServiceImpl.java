package kr.ac.kmu.dbp.service.chat.chatRoom;

import kr.ac.kmu.dbp.repository.chat.chatRoom.ChatRoomInMemoryRepository;
import kr.ac.kmu.dbp.repository.chat.chatRoom.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public ChatRoomServiceImpl(ChatRoomInMemoryRepository chatRoomInMemoryRepository) {
        this.chatRoomRepository = chatRoomInMemoryRepository;
    }

}
