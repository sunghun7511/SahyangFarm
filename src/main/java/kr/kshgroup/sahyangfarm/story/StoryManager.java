package kr.kshgroup.sahyangfarm.story;

import kr.kshgroup.sahyangfarm.ManagerBase;
import kr.kshgroup.sahyangfarm.util.ReflectionUtil;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class StoryManager extends ManagerBase {
    private final List<SFStoryBase> stories = new ArrayList<>();

    @Override
    public void onEnable() {
        registerStories();

        stories.forEach(SFStoryBase::onInit);
    }

    @Override
    public void onRun() {
        stories.forEach(SFStoryBase::onRun);
    }

    @Override
    public void onDisable() {
        stories.forEach(SFStoryBase::onStop);
    }

    private void registerStories() {
        try {
            ReflectionUtil.getClasses(getClass().getPackage().getName()).stream()
                    .filter(SFStoryBase.class::isAssignableFrom)
                    .filter(cls -> !cls.isInterface())
                    .filter(cls -> !Modifier.isAbstract(cls.getModifiers()))
                    .forEach(this::registerStory);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void registerStory(Class<?> cls) {
        try {
            SFStoryBase instance = (SFStoryBase) cls.newInstance();
            stories.add(instance);
        } catch (Exception exception) {
            System.err.println("Error in registering " + cls.getName());
            exception.printStackTrace();
        }
    }

    public <T extends SFStoryBase> T getStory(Class<T> classType) {
        return (T) this.stories.stream()
                .filter(story -> story.getClass().equals(classType))
                .findFirst()
                .orElse(null);
    }
}
