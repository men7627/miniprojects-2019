package techcourse.fakebook.service.chat;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcourse.fakebook.domain.chat.Chat;
import techcourse.fakebook.domain.chat.ChatRepository;
import techcourse.fakebook.domain.user.User;
import techcourse.fakebook.service.chat.assembler.ChatAssembler;
import techcourse.fakebook.service.chat.dto.ChatRequest;
import techcourse.fakebook.service.chat.dto.ChatResponse;
import techcourse.fakebook.service.user.UserService;
import techcourse.fakebook.service.user.dto.UserOutline;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatAssembler chatAssembler;
    private final UserService userService;
    private final SimpMessagingTemplate messanger;

    public ChatService(ChatRepository chatRepository, ChatAssembler chatAssembler, UserService userService, SimpMessagingTemplate messanger) {
        this.chatRepository = chatRepository;
        this.chatAssembler = chatAssembler;
        this.userService = userService;
        this.messanger = messanger;
    }

    @Transactional
    public List<ChatResponse> findByFromUserAndToUser(UserOutline userOutline, Long toUserId) {
        chatRepository.updateReadByFromUserIdAndToUserId(userOutline.getId(), toUserId);
        List<Chat> chats = chatRepository.findByFromUserAndToUserOrToUserAndFromUser(userOutline.getId(), toUserId);
        List<ChatResponse> chatResponses =
                chats.stream()
                .map(chatAssembler::toChatResponse)
                .collect(Collectors.toList());
        messanger.convertAndSend(("/api/chatting"), chatResponses);
        return chatResponses;
    }

    public ChatResponse save(UserOutline userOutline, ChatRequest chatRequest) {
        User fromUser = userService.getUser(userOutline.getId());
        User toUser = userService.getUser(chatRequest.getUserId());
        Chat chat = chatAssembler.toEntity(chatRequest, fromUser, toUser);

        Chat savedChat = chatRepository.save(chat);

        messanger.convertAndSend(("/api/chatting")
                , findByFromUserAndToUser(userOutline, chatRequest.getUserId()));

        return chatAssembler.toChatResponse(savedChat);
    }
}