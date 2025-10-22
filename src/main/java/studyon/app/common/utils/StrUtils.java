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
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StrUtils {

    private static final ObjectMapper objectMapper =
            new ObjectMapper().registerModule(new JavaTimeModule());

    private static final Random random = new Random();
    private static final Base64.Encoder urlEncoder = Base64.getUrlEncoder();

    // Summernote용 Safelist
    private static final Safelist SAFELIST = new Safelist()
            .addTags("p", "div", "br", "hr")
            .addTags("h1", "h2", "h3", "h4", "h5", "h6")
            .addTags("blockquote", "pre", "code")
            .addTags("span", "font", "strong", "b", "em", "i", "u", "s", "strike", "del")
            .addTags("sup", "sub", "small", "mark")
            .addTags("ul", "ol", "li")
            .addTags("table", "thead", "tbody", "tfoot", "tr", "th", "td", "caption")
            .addTags("a", "img")
            .addTags("video", "source", "iframe")
            .addAttributes("span", "style", "class")
            .addAttributes("font", "face", "size", "color", "style")
            .addAttributes("p", "style", "class", "align")
            .addAttributes("div", "style", "class")
            .addAttributes("h1", "style", "class")
            .addAttributes("h2", "style", "class")
            .addAttributes("h3", "style", "class")
            .addAttributes("h4", "style", "class")
            .addAttributes("h5", "style", "class")
            .addAttributes("h6", "style", "class")
            .addAttributes("blockquote", "class")
            .addAttributes("pre", "class")
            .addAttributes("img", "src", "alt", "title", "style", "width", "height", "class")
            .addAttributes("a", "href", "target", "title", "rel")
            .addAttributes("table", "style", "class", "border", "width")
            .addAttributes("tr", "style", "class")
            .addAttributes("td", "style", "class", "colspan", "rowspan", "width", "height")
            .addAttributes("th", "style", "class", "colspan", "rowspan", "width", "height")
            .addAttributes("ul", "style", "class")
            .addAttributes("ol", "style", "class")
            .addAttributes("li", "style", "class")
            .addAttributes("video", "src", "controls", "width", "height", "style")
            .addAttributes("iframe", "src", "width", "height", "style", "frameborder", "allowfullscreen")
            .addProtocols("a", "href", "http", "https", "mailto", "/", "#")
            .addProtocols("img", "src", "http", "https", "/")
            .addProtocols("iframe", "src", "http", "https")
            .addProtocols("video", "src", "http", "https", "/");


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
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();

        // [0] : Thread.getStackTrace
        // [1] : TraceUtils.getMethodLocation
        // [2] : 실제 호출한 메소드
        return stack.length > 2 ?
                "[%s::%s] %s".formatted(clazz.getSimpleName(), stack[2].getMethodName(), message) :
                "[%s::%s] %s".formatted(clazz.getSimpleName(), "UNKNOWN_METHOD", message);
    }


    public static String purifyHtml(String htmlContent) {

        // [1] HTML 파싱
        String html = Jsoup.parse(htmlContent).body().html();

        // [2] 위험 태그 제거 후 반환
        return Jsoup.clean(html, SAFELIST);
    }


    public static List<String> purifyAndExtractImgSrcFromHtml(String htmlContent) {

        // [1] HTML 내 위험 코드 정화
        String purified = purifyHtml(htmlContent);
        Document purifiedDoc = Jsoup.parse(purified);

        // [2] 이미지 태그만 추출 후  src 속성만 추출 후 반환
        return purifiedDoc.select("img").stream()
                .map(img -> img.attr("src"))
                .filter(src -> !src.isBlank())
                .toList();
    }

}
