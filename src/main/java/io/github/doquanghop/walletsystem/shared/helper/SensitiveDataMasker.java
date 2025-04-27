package io.github.doquanghop.walletsystem.shared.helper;

import io.github.doquanghop.walletsystem.shared.annotation.logging.MaskSensitive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.StreamSupport;

@Component
public class SensitiveDataMasker {
    private static final Logger LOGGER = LoggerFactory.getLogger(SensitiveDataMasker.class);

    public Object[] maskSensitiveFields(Object[] args, String maskValue) {
        if (args == null) {
            return null;
        }
        return Arrays.stream(args)
                .map(arg -> maskSensitiveObject(arg, maskValue))
                .toArray();
    }

    public Object maskSensitiveObject(Object obj, String maskValue) {
        if (obj == null) {
            return null;
        }

        try {
            if (obj.getClass().isArray()) {
                return maskSensitiveFields((Object[]) obj,maskValue);
            }

            if (obj instanceof Iterable<?> iterable) {
                return StreamSupport.stream(iterable.spliterator(), false)
                        .map(item -> maskSensitiveObject(item, maskValue))
                        .toList();
            }

            Class<?> clazz = obj.getClass();
            if (clazz.isRecord()) {
                return maskRecord(obj, clazz, false,maskValue);
            } else {
                return maskRegularObject(obj, clazz,maskValue);
            }
        } catch (Exception e) {
            return obj;
        }
    }


    private Object maskRegularObject(Object obj, Class<?> clazz, String maskValue) throws Exception {
        Object maskedObj = clazz.getDeclaredConstructor().newInstance();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object fieldValue = field.get(obj);
            if (field.isAnnotationPresent(MaskSensitive.class)) {
                if (isSimpleType(field.getType())) {
                    field.set(maskedObj, "****");
                } else {
                    field.set(maskedObj, maskSensitiveObject(fieldValue,maskValue));
                }
            } else if (shouldRecursivelyMask(field.getType(), fieldValue)) {
                field.set(maskedObj, maskSensitiveObject(fieldValue,maskValue));
            } else {
                field.set(maskedObj, fieldValue);
            }
        }
        return maskedObj;
    }

    private Object maskRecord(Object obj, Class<?> clazz, boolean maskAllFields,String maskValue) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        Object[] fieldValues = new Object[fields.length];

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Object fieldValue = field.get(obj);
            LOGGER.debug("Xử lý trường {} của {}: giá trị gốc={}", field.getName(), clazz.getSimpleName(), fieldValue);

            if (maskAllFields) {
                // Mask tất cả các trường
                fieldValues[i] = isSimpleType(field.getType()) ? "****" : null;
                LOGGER.debug("Trường {} được mask toàn bộ: giá trị mask={}", field.getName(), fieldValues[i]);
            } else if (field.isAnnotationPresent(MaskSensitive.class)) {
                MaskSensitive mask = field.getAnnotation(MaskSensitive.class);
                if (isSimpleType(field.getType())) {
                    fieldValues[i] = mask.maskValue();
                    LOGGER.debug("Trường {} được mask: giá trị mask={}", field.getName(), fieldValues[i]);
                } else {
                    fieldValues[i] = maskRecord(fieldValue, field.getType(), true,maskValue);
                    LOGGER.debug("Trường {} được mask đệ quy (tất cả trường): giá trị mask={}", field.getName(), fieldValues[i]);
                }
            } else {
                fieldValues[i] = shouldRecursivelyMask(field.getType(), fieldValue)
                        ? maskSensitiveObject(fieldValue,maskValue)
                        : fieldValue;
                LOGGER.debug("Trường {}: giá trị sau xử lý={}", field.getName(), fieldValues[i]);
            }
        }

        Constructor<?> constructor = clazz.getDeclaredConstructor(
                Arrays.stream(fields)
                        .map(Field::getType)
                        .toArray(Class[]::new)
        );
        constructor.setAccessible(true);
        LOGGER.debug("Gọi constructor cho {} với các tham số: {}", clazz.getSimpleName(), Arrays.toString(fieldValues));
        return constructor.newInstance(fieldValues);
    }

    private boolean shouldRecursivelyMask(Class<?> fieldType, Object fieldValue) {
        return fieldValue != null
                && !fieldType.isPrimitive()
                && !fieldType.getPackageName().startsWith("java.")
                && !fieldType.getPackageName().startsWith("javax.");
    }

    private boolean isSimpleType(Class<?> fieldType) {
        return fieldType == String.class
                || fieldType.isPrimitive()
                || Number.class.isAssignableFrom(fieldType)
                || Boolean.class == fieldType
                || Character.class == fieldType;
    }
}