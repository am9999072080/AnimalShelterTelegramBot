package Telegram.Bot.AnimalShelter.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class ObjectUpdater {
    /**
     *Метод обновляет информацию (без полей null) копируя ее из старого объекта в новый
     *
     * @param oldObject Старый объект из которого берется информация
     * @param newObject Новый объект в который помещается информация
     */
    public static void oldToNew(Object oldObject, Object newObject) {
        Logger logger = LoggerFactory.getLogger(ObjectUpdater.class);
        Field[] fields = oldObject.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(oldObject);
                if (value != null) {
                    field.set(newObject, value);
                }
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
