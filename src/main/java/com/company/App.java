package com.company;

import com.company.model.PromoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class App {

    public static void main(String[] args) throws IOException, IntrospectionException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();

//        gen(objectMapper, "/Param.json", KeyValue.class.getName());
        gen(objectMapper, "/PromoEntity.json", PromoEntity.class.getName());


    }

    private static void gen(ObjectMapper objectMapper, String jsonPath, String pojoName) throws ClassNotFoundException, IOException, IllegalAccessException, InvocationTargetException {
        InputStream inputStream = App.class.getResourceAsStream(jsonPath);

        Class<?> targetClass = Class.forName(pojoName);

        Object parsedObject = objectMapper.readValue(inputStream, targetClass);
        prepareObject(targetClass, parsedObject);
    }


    private static String prepareObject(Class<?> targetClass, Object parsedObject) throws IllegalAccessException, InvocationTargetException {
        return prepareObject(targetClass, parsedObject, null);
    }

    private static String prepareObject(Class<?> targetClass, Object parsedObject, String objectName) throws IllegalAccessException, InvocationTargetException {
        String className = targetClass.getSimpleName();
        if (objectName == null) {
            objectName = StringUtils.uncapitalize(className);
        }
        System.out.println(className + " " + objectName + " = new " + className + "()");


        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(targetClass);

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if ("class".equals(propertyDescriptor.getName())) {
                continue;
            }

            String name = propertyDescriptor.getName();
            Method writeMethod = propertyDescriptor.getWriteMethod();
            Method readMethod = propertyDescriptor.getReadMethod();
            Class<?> propertyType = propertyDescriptor.getPropertyType();
            Object value = readMethod.invoke(parsedObject);

            if (value instanceof String) {
                value = "\"" + value + "\"";
            }

            PropertyDescriptor[] innerPropertyDescriptors = PropertyUtils.getPropertyDescriptors(propertyType);
            if (innerPropertyDescriptors.length != 0 &&
                    Arrays.stream(innerPropertyDescriptors).anyMatch(pd -> pd.getWriteMethod() != null)) {

                value = prepareObject(propertyType, value, name);
            }


            System.out.println(objectName + "." + writeMethod.getName() + "(" + value + ")");

        }

//        System.out.println();
        return objectName;

    }
}
