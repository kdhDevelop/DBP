package kr.ac.kmu.dbp.repository.chat.chatRoom;

import kr.ac.kmu.dbp.entity.chat.chatRoom.ChatRoom;
import kr.ac.kmu.dbp.entity.employee.Employee;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatRoomInMemoryRepository implements ChatRoomRepository {

    //채팅방(ChatRoom)
    private static final Vector<ChatRoom> chatRoomList = new Vector<>();

    //사람(Employee)에 해당하는 연결(WebSocketSession)
    private static final ConcurrentHashMap<Employee, WebSocketSession> userSessionList = new ConcurrentHashMap<>();

    @Override
    public void create(ChatRoom chatRoom) {
        chatRoomList.add(new ChatRoom(chatRoomList.size()+1, chatRoom.getName()));
    }

    @Override
    public ChatRoom readByPid(int pid) {
        for (ChatRoom chatRoom : chatRoomList) {
            if (chatRoom.getPid() == pid) {
                return chatRoom;
            }
        }
        throw new RuntimeException();
    }
}
