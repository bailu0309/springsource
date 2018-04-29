package springmvc.framework.context.support;

import springmvc.framework.beans.BeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author mabailu
 * @Date 2018/4/28 11:05
 * @Description 对配置文件进行查找、解析
 */
public class BeanDefinitionReader {

    private Properties config = new Properties();

    private List<String> registryBeanClasses = new ArrayList<String>();

    private final String SCAN_PACKAGE = "scanPackage";

    public BeanDefinitionReader(String[] configLocations) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(configLocations[0].
                replace("classpath:", ""));

        try {
            config.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        doScanner(config.getProperty(SCAN_PACKAGE));

    }

    /**
     * 扫描包下的class，并且保存
     *
     * @param packageName 包名
     */
    private void doScanner(String packageName) {
        URL url = this.getClass().getClassLoader().
                getResource("/" +
                        packageName.replaceAll("\\.", "/"));
        File classDir = new File(url.getFile());

        for (File file : classDir.listFiles()) {
            if (file.isDirectory()) {
                doScanner(packageName + "." + file.getName());
            } else {
                registryBeanClasses.add(packageName + "." + file.getName().
                        replaceAll(".class", ""));
            }
        }
    }

    public List<String> loadBeanDefinitions() {
        return this.registryBeanClasses;
    }


    public BeanDefinition registerBean(String className) {
        if (this.registryBeanClasses.contains(className)) {
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setBeanClassName(className);
            beanDefinition.setFactoryBeanName(lowerFirstCase(
                    className.substring(className.lastIndexOf("." + 1))));

            return beanDefinition;

        }
        return null;
    }

    private String lowerFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

}
