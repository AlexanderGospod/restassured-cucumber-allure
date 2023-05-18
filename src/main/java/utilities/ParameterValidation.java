package utilities;

import jakarta.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;


public class ParameterValidation {
    public void checkThatAllParametersAnnotatedNotNullAreNotEqualNull(Object object) {
        // Perform validation using Hibernate Validator
            //Check that parameter annotated '@NotNull is not equal null
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(object);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                NotNull annotation = field.getAnnotation(NotNull.class);
                if (value == null && annotation != null) {
                    throw new IllegalArgumentException("Field " + field.getName() + " cannot be null");
                }

                //If the parameter is an object with parameters, then this method is run recursively to check all its parameter
                if (value != null && (!isJavaStandardType(field.getType()) || isParameterizedList(field.getGenericType()))) {
                    if (isParameterizedList(field.getGenericType())) {
                        List<?> list = (List<?>) value;
                        for (Object listItem : list) {
                            checkThatAllParametersAnnotatedNotNullAreNotEqualNull(listItem);
                        }
                    } else if (field.getType().isArray()) {
                        Object[] array = (Object[]) value;
                        for (Object item : array) {
                            checkThatAllParametersAnnotatedNotNullAreNotEqualNull(item);
                        }
                    } else {
                        checkThatAllParametersAnnotatedNotNullAreNotEqualNull(value);
                    }
                }
            }
    }

    private boolean isJavaStandardType(Class<?> type) {
        return type.isPrimitive() || type.getName().startsWith("java.");
    }

    private boolean isParameterizedList(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            return rawType == List.class;
        }
        return false;
    }
}
