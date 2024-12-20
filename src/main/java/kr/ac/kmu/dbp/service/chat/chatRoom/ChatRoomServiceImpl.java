package kr.ac.kmu.dbp.service.chat.chatRoom;

import kr.ac.kmu.dbp.dto.chat.chatRoom.ChatRoomDtoCreate;
import kr.ac.kmu.dbp.dto.chat.chatRoom.ChatRoomDtoRead;
import kr.ac.kmu.dbp.entity.chat.chatRoom.ChatRoom;
import kr.ac.kmu.dbp.repository.chat.chatRoom.ChatRoomInMemoryRepository;
import kr.ac.kmu.dbp.repository.chat.chatRoom.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public ChatRoomServiceImpl(ChatRoomInMemoryRepository chatRoomInMemoryRepository) {
        this.chatRoomRepository = chatRoomInMemoryRepository;
    }

    @Override
    public void create(ChatRoomDtoCreate chatRoomDtoCreate) {
        chatRoomRepository.create(new ChatRoom(chatRoomDtoCreate));
    }

    @Override
    public ChatRoom readByPid(int pid) {
        return chatRoomRepository.readByPid(pid);
    }

    @Override
    public List<ChatRoomDtoRead> readAll() {
        List<ChatRoomDtoRead> result = new ArrayList<>();

        List<ChatRoom> chatRoomList = chatRoomRepository.readAll();
        for (ChatRoom chatRoom : chatRoomList) {
            result.add(new ChatRoomDtoRead(chatRoom));
        }

        return result;
    }
}
