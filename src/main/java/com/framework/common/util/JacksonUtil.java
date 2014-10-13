package com.framework.common.util;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-8 20:42
 * Email:johnny_lx@yahoo.com
 */
public class JacksonUtil {


    private static Logger logger = LoggerFactory.getLogger(JacksonUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
                jgen.writeString("");
            }
        });
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    private static Integer getKeyIndex(String key) {
        int left = key.indexOf("[");
        int right = key.indexOf("]");
        if (left == -1 || right == -1) {
            return null;
        }
        String index = key.substring(left + 1, right);
        try {
            return Integer.valueOf(index);
        } catch (Exception e) {
            return -1;
        }
    }

    private static Object getJsonObject(Object object, String key) {
        if (object instanceof JsonNode) {
            JsonNode obj = (JsonNode) object;
            if (obj.isArray()) {
                List retList = new ArrayList();
                for (int i = 0; i < obj.size(); i++) {
                    JsonNode proc = obj.get(i);
                    if (key.endsWith("[]")) {
                        retList.add(getJsonNode(proc, key.substring(0, key.length() - 2)));
                    } else {
                        Integer index = getKeyIndex(key);
                        if (index == null) {
                            retList.add(getJsonNode(proc, key));
                        } else if (index == -1) {
                            retList.add(getJsonNode(proc, key.substring(0, key.indexOf("["))));
                        } else {
                            retList.add(getJsonNodeIndex(proc, key.substring(0, key.indexOf("[")), index));
                        }

                    }
                }
                return retList;
            } else {
                if (key.endsWith("[]")) {
                    return getJsonNode(obj, key.substring(0, key.length() - 2));
                } else {
                    Integer index = getKeyIndex(key);
                    if (index == null) {
                        return getJsonNode(obj, key);
                    } else if (index == -1) {
                        return getJsonNode(obj, key.substring(0, key.indexOf("[")));
                    } else {
                        return getJsonNodeIndex(obj, key.substring(0, key.indexOf("[")), index);
                    }
                }
            }
        } else if (object instanceof List) {
            List list = (List) object;
            List retList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                Object jo = list.get(i);
                Object ret = getJsonObject(jo, key);
                if (ret instanceof List) {
                    retList.addAll((List) ret);
                } else {
                    retList.add(ret);
                }
            }
            return retList;
        }
        return null;
    }

    public static JsonNode getJsonNode(JsonNode obj, String key) {
        if (obj == null) {
            return null;
        }
        return obj.findValue(key);
    }

    public static JsonNode getJsonNodeIndex(JsonNode obj, String key, int index) {
        if (obj == null || index < 0) {
            return null;
        }

        JsonNode node = obj.findValue(key);

        if (node != null && node.isArray() && node.size() > index) {
            return node.get(index);
        } else {
            return null;
        }
    }

    /**
     * 根据json对象直接获取指定路径keys对应的json对象
     *
     * @param obj
     * @param keys 如 new String[]{"A","B[]","C[1]","D"}   其中B[]表示取全部数组，C[1]表示取index为1的数据，从0开始
     * @return
     */
    public static List getJsonObject(JsonNode obj, String... keys) {
        return getJsonObject(obj, null, keys);
    }

    /**
     * 获取泛型的Collection Type add by yantao.zhang
     * @param collectionClass 泛型的Collection
     * @param elementClasses 元素类
     * @return
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses){
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * get JsonNode value add by yantao.zhang
     * @param jsonObject
     * @param key
     * @return
     */
    public static JsonNode getJsonValue(JsonNode jsonObject, String key) {
        if (jsonObject == null || key == null || !jsonObject.has(key)) {
            return null;
        }
        return jsonObject.findValue(key);
    }

    /**
     * 根据json对象直接获取指定路径keys对应的json对象
     *
     * @param obj
     * @param clazz 指定转化后的对象
     * @param keys  如 new String[]{"A","B[]","C[1]","D"}   其中B[]表示取全部数组，C[1]表示取index为1的数据，从0开始
     * @return
     */
    public static <T> List<T> getJsonObject(JsonNode obj, Class<T> clazz, String... keys) {
        if (obj == null || keys == null || keys.length == 0) {
            return null;
        }
        Object proc = obj;
        for (String key : keys) {
            if (proc == null) return null;
            if (key == null) {
                throw new RuntimeException("Json转换时发现无效的键值");
            } else {
                proc = getJsonObject(proc, key);
            }
        }
        if (proc instanceof List) {
            if (clazz != null) {
                try {
                    List<T> ret = new ArrayList<T>();
                    for (Object o : (List) proc) {
                        ret.add(getInstance().readValue((JsonNode) o, clazz));
                    }
                    return ret;
                } catch (Exception e) {
                    logger.error("类型转换错误：" + e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            } else {
                return (List) proc;
            }
        } else {
            try {
                if (clazz != null) {
                    List<T> ret = new ArrayList<T>();
                    ret.add(getInstance().readValue((JsonNode) proc, clazz));
                    return ret;
                } else {
                    List ret = new ArrayList();
                    ret.add(proc);
                    return ret;
                }
            } catch (Exception e) {
                logger.error("类型转换错误：" + e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
    }


    public static void main(String[] args) {
        ObjectNode jo = new ObjectNode(JsonNodeFactory.instance);
        ObjectNode joA = new ObjectNode(JsonNodeFactory.instance);
        ArrayNode jrA1 = new ArrayNode(JsonNodeFactory.instance);
        ObjectNode joAA1 = new ObjectNode(JsonNodeFactory.instance);
        ObjectNode joAA1A = new ObjectNode(JsonNodeFactory.instance);
        joAA1A.put("a1", "aa1a1");
        joAA1A.put("a2", "aa1a2");
        joAA1A.put("a3", "aa1a3");
        ObjectNode joAA1B = new ObjectNode(JsonNodeFactory.instance);
        joAA1B.put("b1", "aa1b1");
        joAA1B.put("b2", "aa1b2");
        joAA1B.put("b3", "aa1b3");
        joAA1.put("asp", joAA1A);
        joAA1.put("bsp", joAA1B);
        ObjectNode joAA2 = new ObjectNode(JsonNodeFactory.instance);
        ObjectNode joAA2A = new ObjectNode(JsonNodeFactory.instance);
        joAA2A.put("a1", "aa2a1");
        joAA2A.put("a2", "aa2a2");
        joAA2A.put("a3", "aa2a3");
        ObjectNode joAA2B = new ObjectNode(JsonNodeFactory.instance);
        joAA2B.put("b1", "aa2b1");
        joAA2B.put("b2", "aa2b2");
        joAA2B.put("b3", "aa2b3");
        joAA2.put("asp", joAA2A);
        joAA2.put("bsp", joAA2B);


        jrA1.add(joAA1);
        jrA1.add(joAA2);
        joA.put("ar", jrA1);
        joAA1.put("a", joAA1A);
        joAA1.put("b", joAA1B);
        joAA2.put("a", joAA2A);
        joAA2.put("b", joAA2B);
        ObjectNode joB = new ObjectNode(JsonNodeFactory.instance);
        ObjectNode joBA = new ObjectNode(JsonNodeFactory.instance);
        joBA.put("a1", "ba1");
        joBA.put("a2", "ba2");
        JsonNode joBB = new ObjectNode(JsonNodeFactory.instance);
        joBA.put("b1", "bb1");
        joBA.put("b2", "bb2");

        joB.put("At", joBA);
        joB.put("ef", joBB);
        jo.put("A", joA);
        jo.put("B", joB);

        System.out.println(jo.toString());

        List list = getJsonObject(jo, new String[]{"A", "ar[0]", "asp"});
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                ObjectNode jobb = (ObjectNode) list.get(i);
                jobb.put("a1", "test");
                System.out.println(list.get(i));
            }
        }

        List<String> lista = getJsonObject(jo,String.class, new String[]{"A", "ar[0]", "asp","a1"});
        if (lista != null) {
            for (int i = 0; i < lista.size(); i++) {
                System.out.println(lista.get(i));
            }
        }
    }
}
