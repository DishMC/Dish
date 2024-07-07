package net.ouja.dish.plugins;

import net.ouja.api.event.EventListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class RegisteredEvents {
    public static final ArrayList<Method> listeners = new ArrayList<>();
    public static final ArrayList<Class<?>> classes = new ArrayList<>();

    public RegisteredEvents(Method method, Class<?> reflectorClass) {
        listeners.add(method);
        classes.add(reflectorClass);
    }

    public static void callEvent(EventListener eventListener) {
        for (int i = 0; i < listeners.size(); i++) {
            Method method = listeners.get(i);
            Class<?> clazz = classes.get(i);
            if (method.getParameterTypes()[0].toString().equals(eventListener.getClass().toString())) {
                try {
                    method.setAccessible(true);
                    method.invoke(clazz.newInstance(), eventListener);
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
