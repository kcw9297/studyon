package studyon.app.layer.domain.banner;

import lombok.*;
import studyon.app.layer.domain.file.FileDTO;

/**
 * 배너 정보를 담은 DTO
 * @version 1.0
 * @author kcw97
 */

@NoArgsConstructor
public class BannerDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Read {

        private Integer idx;
        private String title;
        private Boolean isActivate;
        private FileDTO.Read bannerImage;
    }

}
