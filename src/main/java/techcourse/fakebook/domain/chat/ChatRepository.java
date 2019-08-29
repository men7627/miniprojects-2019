package techcourse.fakebook.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import techcourse.fakebook.domain.article.Article;
import techcourse.fakebook.domain.comment.Comment;
import techcourse.fakebook.domain.user.User;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query(value = "select * from Chat where (FROM_USER_ID = ?1 and TO_USER_ID =?2) or (FROM_USER_ID = ?2 and TO_USER_ID =?1)", nativeQuery = true)
    List<Chat> findByFromUserAndToUserOrToUserAndFromUser(Long fromUserId, Long toUserId);
}
