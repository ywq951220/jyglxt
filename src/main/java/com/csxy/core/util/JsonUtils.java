package com.csxy.core.util;

import com.csxy.core.common.JsonDate;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    @SuppressWarnings("rawtypes")
    public static JsonConfig getJsonConfig(String dateFormat){
        JsonDate beanProcessor = new JsonDate();
        if(dateFormat != null){
            DateFormat df = new SimpleDateFormat(dateFormat);
            beanProcessor.setDateFormat(df);
        }

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(Date.class, beanProcessor);
        String defaultValue = "null";
        return jsonConfig;
    }

    public static String getJsonData(Object bean){
        return JSONObject.fromObject(bean, getJsonConfig(null)).toString();
    }


    public static Object getBeanFromJsonData(String data, Class<?> beanClass){
        if(logger.isDebugEnabled()){
            logger.debug("getBeanFromJsonData:[" + data + "]");
        }
        JSONObject jsonObject = JSONObject.fromObject(data);
        return JSONObject.toBean(jsonObject, beanClass);
    }
}
