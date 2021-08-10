package kr.kshgroup.sahyangfarm;

import kr.kshgroup.sahyangfarm.util.ReflectionUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SahyangFarm extends JavaPlugin {

    private static SahyangFarm instance;

    public static SahyangFarm getInstance() {
        return instance;
    }

    public SahyangFarm() {
        SahyangFarm.instance = this;
    }

    private final List<ManagerBase> managers = new ArrayList<>();

    @Override
    public void onEnable() {
        getLogger().info("Plugin enabled.");

        registerManagers();

        // 각 클래스끼리 종속성을 최소화하기 위해 onEnable -> onRun 순서로 호출
        managers.forEach(ManagerBase::onEnable);
        managers.forEach(ManagerBase::onRun);
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }

    /**
     * ManagerBase 클래스를 상속한 클래스의 인스턴스를 만들고 managers에 등록합니다.
     */
    private void registerManagers() {
        try {
            ReflectionUtil.getClasses(this.getClass().getPackage().getName())
                    .stream()
                    .filter(ManagerBase.class::isAssignableFrom)
                    .filter(cls -> cls != ManagerBase.class)
                    .map(cls -> {
                        try {
                            return cls.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .map(instance -> (ManagerBase) instance)
                    .forEach(managers::add);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
