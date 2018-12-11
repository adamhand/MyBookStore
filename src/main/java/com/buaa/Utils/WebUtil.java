package com.buaa.Utils;

import com.buaa.domain.Category;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

public class WebUtil {
    public static <T> T fillBean(HttpServletRequest request, Class<T> clazz)
    {
        try {
            T bean = clazz.newInstance();
            /**
             * BeanUtils.populate用来将一些key-value的值映射到bean中的属性。
             * BeanUtils.populate( Object bean, Map properties )，
             * 这个方法会遍历map<key, value>中的key，如果bean中有这个属性，就把这个key对应的value值赋给bean的属性。
             */
            BeanUtils.populate(bean, request.getParameterMap());
            return bean;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static Category fillBean(String name, Class<Category> clazz)
    {
        return null;
    }
}
