package kr.kshgroup.sahyangfarm.listener;

import kr.kshgroup.sahyangfarm.ManagerBase;
import kr.kshgroup.sahyangfarm.SahyangFarm;
import kr.kshgroup.sahyangfarm.util.ReflectionUtil;
import org.bukkit.Bukkit;

import java.lang.reflect.Modifier;

public class ListenerManager extends ManagerBase {
    @Override
    public void onRun() {
        registerBukkitListeners();
    }

    private void registerBukkitListeners() {
        try {
            ReflectionUtil.getClasses(getClass().getPackage().getName()).stream()
                    .filter(SFListener.class::isAssignableFrom)
                    .filter(cls -> !cls.isInterface())
                    .filter(cls -> !Modifier.isAbstract(cls.getModifiers()))
                    .forEach(this::registerBukkitListenerClass);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void registerBukkitListenerClass(Class<?> cls) {
        try {
            SFListener instance = (SFListener) cls.newInstance();
            Bukkit.getPluginManager().registerEvents(instance, SahyangFarm.getInstance());
            System.out.println("Registered event listener " + cls.getName());
        } catch (Exception exception) {
            System.err.println("Error in registering " + cls.getName());
            exception.printStackTrace();
        }
    }
}
