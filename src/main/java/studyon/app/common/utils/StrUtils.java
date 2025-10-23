package studyon.app.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.springframework.web.util.UriUtils;
import studyon.app.common.exception.UtilsException;

import java.nio.charset.StandardCharsets;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StrUtils {

    private static final ObjectMapper objectMapper =
            new ObjectMapper().registerModule(new JavaTimeModule());

    private static final Random random = new Random();
    private static final Base64.Encoder urlEncoder = Base64.getUrlEncoder();

    // Summernote용 Safelist
    private static final Safelist SAFELIST = Safelist.relaxed()
            .addTags("font", "mark", "video", "source", "iframe")
            // img src 허용
            .addAttributes("img", "src", "style", "width", "height", "class")
            .addAttributes("span", "style", "class")
            .addAttributes("font", "face", "size", "color", "style")
            .addAttributes("p", "style", "class", "align")
            .addAttributes("div", "style", "class", "align")
            .addAttributes("h1", "style", "class")
            .addAttributes("h2", "style", "class")
            .addAttributes("h3", "style", "class")
            .addAttributes("h4", "style", "class")
            .addAttributes("h5", "style", "class")
            .addAttributes("h6", "style", "class")
            .addAttributes("blockquote", "style", "class")
            .addAttributes("pre", "style", "class")
            .addAttributes("code", "style", "class")
            .addAttributes("strong", "style", "class")
            .addAttributes("em", "style", "class")
            .addAttributes("u", "style", "class")
            .addAttributes("a", "href", "target", "rel") // href 추가
            .addAttributes("table", "style", "class", "border", "width", "cellpadding", "cellspacing")
            .addAttributes("tr", "style", "class")
            .addAttributes("td", "style", "class", "colspan", "rowspan", "width", "height")
            .addAttributes("th", "style", "class", "colspan", "rowspan", "width", "height", "scope")
            .addAttributes("thead", "style", "class")
            .addAttributes("tbody", "style", "class")
            .addAttributes("ul", "style", "class")
            .addAttributes("ol", "style", "class", "start", "type")
            .addAttributes("li", "style", "class")
            .addAttributes("video", "src", "controls", "width", "height", "style", "class")
            .addAttributes("source", "src", "type")
            .addAttributes("iframe", "src", "width", "height", "style", "class", "frameborder", "allowfullscreen")
            .preserveRelativeLinks(true);

    public static String toJson(Object data) {

        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new UtilsException("-> JSON String 직렬화에 실패했습니다!", e);
        }
    }


    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            throw new UtilsException("-> DTO Object 역직렬화에 실패했습니다!", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new UtilsException("-> DTO Object 역직렬화에 실패했습니다!", e);
        }
    }



    public static String encodeToUTF8(String text) {

        try {
            return UriUtils.encode(text, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new UtilsException("-> UTF8 인코딩에 실패했습니다!", e);
        }
    }


    public static String createRandomNumString(int length) {

        // [1] 숫자 format, 랜덤 수 범위 계산
        String format = "%0" + length + "d";
        int randomRange = (int) Math.pow(10, length); // 10^length

        // [2] 랜덤 수 생성 및 반환
        return format.formatted(random.nextInt(randomRange));
    }

    public static String createRandomNumString(int length, String prefix) {

        // [1] 숫자 format, 랜덤 수 범위 계산
        String format = "%s%0" + length + "d";
        int randomRange = (int) Math.pow(10, length); // 10^length

        // [2] 랜덤 수 생성 및 반환
        return format.formatted(prefix, random.nextInt(randomRange));
    }


    public static String createShortUUID() {

        // [1] 랜덤 문자열 생성 (UUID)
        UUID uuid = UUID.randomUUID();

        // [2] byte 배열로 변환
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] bytes = new byte[16];
        for (int i = 0; i < 8; i++) {
            bytes[i]     = (byte)(msb >>> 8 * (7 - i));
            bytes[8 + i] = (byte)(lsb >>> 8 * (7 - i));
        }

        // [3] 22자 길이의 UUID로 인코딩 후 반환
        return urlEncoder.withoutPadding().encodeToString(bytes);
    }


    public static String createLogStr(Class<?> clazz, String message) {

        String caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                .walk(frames ->
                        frames.skip(1).findFirst().map(StackWalker.StackFrame::getMethodName).orElse("UNKNOWN")
                );

        return "[%s::%s] %s".formatted(clazz.getSimpleName(), caller, message);
    }


    public static String purifyHtml(String htmlContent) {

        return Objects.isNull(htmlContent) || htmlContent.isBlank() ?
                "" :
                Jsoup.clean(
                        htmlContent,
                        "https://example.com", // 상대경로 허용을 위한 더미 주소
                        SAFELIST, // custom safelist
                        new Document.OutputSettings().prettyPrint(false) // 개행문자 허용
                );
    }


    public static List<String> purifyAndExtractFileNameFromHtml(String htmlContent) {

        // [1] HTML 내 위험 코드 정화
        String purified = purifyHtml(htmlContent);
        Document purifiedDoc = Jsoup.parse(purified);

        // [2] 이미지 태그만 추출 후  src 속성만 추출 후 반환
        return purifiedDoc.select("img").stream()
                .map(img -> img.attr("src"))
                .filter(src -> !src.isBlank())
                .map(src -> src.substring(src.lastIndexOf('/') + 1))
                .toList();
    }

}
