package studyon.app.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriUtils;
import studyon.app.common.exception.UtilsException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StrUtils {

    private static final ObjectMapper objectMapper =
            new ObjectMapper().registerModule(new JavaTimeModule());

    private static final Random random = new Random();
    private static final Base64.Encoder urlEncoder = Base64.getUrlEncoder();


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

}
