package kr.kshgroup.sahyangfarm.util;

import kr.kshgroup.sahyangfarm.SahyangFarm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ReflectionUtil {
    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     */
    public static List<Class<?>> getClasses(String packageName) throws IOException {
        String path = packageName.replace('.', '/');
        ArrayList<Class<?>> classes = new ArrayList<>();

        File jarFile = new File(SahyangFarm.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        JarFile jar = new JarFile(jarFile);
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.isDirectory()) continue;

            String name = entry.getName();
            if (!name.startsWith(path + "/")) continue;

            String className = name.replace("/", ".");
            if (className.endsWith(".class"))
                className = className.substring(0, className.length() - 6);

            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignored) {
            }
        }
        jar.close();
        return classes;
    }
}
