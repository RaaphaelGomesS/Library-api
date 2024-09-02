package mock.utils;

public interface IConvertJson {

    public <T> T convertToJson(String json, Class<T> tClass);
}
