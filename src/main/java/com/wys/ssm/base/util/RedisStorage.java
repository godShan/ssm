package com.wys.ssm.base.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.io.*;
import java.util.Properties;

import static com.google.common.collect.Sets.newHashSet;


public class RedisStorage{
    private static Logger logger = LoggerFactory.getLogger(RedisStorage.class);

    private static RedisStorage instance;

    private final static String CONFIG_FILE = "redis.properties";
    private final static String MASTER_NAME = "redis.master.name";
    private final static String REDIS_SENTINELS = "redis.sentinels";

    private static Object LOCK = new Object();
    private JedisSentinelPool sentinelPool;

    private RedisStorage() {
        InputStream inputStream = null;
        try {
            ClassLoader clsLoader = Thread.currentThread().getContextClassLoader();
            inputStream = clsLoader.getResourceAsStream(CONFIG_FILE);
            Properties properties = new Properties();
            properties.load(inputStream);

            String[] redisSentinels = properties.getProperty(REDIS_SENTINELS).replaceAll(" ", "").split(",");
            sentinelPool = new JedisSentinelPool(properties.getProperty(MASTER_NAME), newHashSet(redisSentinels));
        } catch (Throwable e) {
            logger.error("Can not init the redis store with properties file: {}", CONFIG_FILE);
            logger.error("Redis store init error ", e);

            throw new IllegalStateException(e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }


    public static RedisStorage getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    logger.info("Init the redis store");
                    instance = new RedisStorage();
                }
            }
        }
        return instance;
    }

    public boolean replace(String key, int expireSeconds, Object obj) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }

        if (obj == null) {
            return true;
        }

        try (Jedis jedis = sentinelPool.getResource()) {
            String result = jedis.setex(key.getBytes(), expireSeconds, serializeObject(obj));
            logger.debug("SET A OBJECT: KEY:" + key + ", OBJ:" + obj + " result:" + result);
            return "OK".equals(result);
        } catch (Exception e) {
            logger.error("UNABLE TO REPLACE KEY: {}", key, e);
        }

        return false;
    }

    public boolean set(String key, int expireSeconds, Object obj) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        if (obj == null) {
            return true;
        }

        try (Jedis jedis = sentinelPool.getResource()) {
            String result = jedis.setex(key.getBytes(), expireSeconds, serializeObject(obj));

            logger.debug("SET A OBJECT: KEY:" + key + ", OBJ:" + obj + "result:" + result);
            return "OK".equals(result);
        } catch (Exception e) {
            logger.error("ERROR: Can not set the object with key: {}", key, e);
        }

        return false;
    }

    public boolean putString(String key, String value, Long timeout){
        return  set(key,timeout.intValue(),value);
    }

    public String getString(String key){
        return (String) get(key);
    }

    public Object get(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        try (Jedis jedis = sentinelPool.getResource()) {
            byte[] item = jedis.get(key.getBytes());

            if (null == item) {
                return null;
            }

            Object obj = deserializeObject(item);
            logger.debug("GET A OBJECT: KEY:" + key + " OBJ:" + obj);
            return obj;
        } catch (Exception e) {
            logger.error("UNABLE TO GET REDIS KEY {}", key, e);
        }
        return null;
    }

    public boolean delete(String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        try (Jedis jedis = sentinelPool.getResource()) {
            logger.info("REMOVE REDIS KEY: " + key);
            return jedis.del(key) > 0;
        } catch (Exception e) {
            logger.error("UNABLE TO REMOVE REDIS KEY {}", key, e);
        }

        return true;
    }

    public static byte[] serializeObject(Object obj) throws Exception {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(256);
        Serializer serializer = new Serializer();
        try {
            serializer.serialize(obj, byteStream);
            return byteStream.toByteArray();
        } catch (Throwable e) {
            throw new Exception("Failed to serialize object using " +
                    serializer.getClass().getSimpleName(), e);
        }
    }

    public static Object deserializeObject(byte[] b) throws Exception {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(b);
        Serializer serializer = new Serializer();
        try {
            return serializer.deserialize(byteStream);
        } catch (Throwable ex) {
            throw new Exception("Failed to deserialize payload. Is the byte array a result of corresponding serialization for " +
                    serializer.getClass().getSimpleName() + "?", ex);
        }
    }

    public static class Serializer {
        public void serialize(Object object, OutputStream outputStream) throws IOException {
            if (!(object instanceof Serializable)) {
                throw new IllegalArgumentException(getClass().getSimpleName() + " requires a Serializable payload " +
                        "but received an object of type [" + object.getClass().getName() + "]");
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        }

        public Object deserialize(InputStream inputStream) throws IOException {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            try {
                return objectInputStream.readObject();
            } catch (ClassNotFoundException ex) {
                throw new IOException("Failed to deserialize object type", ex);
            }
        }
    }
}
