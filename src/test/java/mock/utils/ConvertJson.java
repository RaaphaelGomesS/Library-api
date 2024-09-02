package mock.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import static org.mockito.Mockito.mock;

@Slf4j
public class ConvertJson implements IConvertJson {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T convertToJson(String json, Class<T> tClass) {
        try {
            return mapper.convertValue(json, tClass);
        } catch (Exception e) {
            log.info("Erro ao converter json do tipo {}, retornando mock default.", tClass);
            return mock(tClass);
        }
    }
}
