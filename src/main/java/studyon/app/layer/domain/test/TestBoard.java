package studyon.app.layer.domain.test;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class TestBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 게시글 내용 수정
    public void editContent(String content) {
        this.content = content;
    }
}
