package studyon.app.layer.controller.test;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TestCache implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String content;
}
