package springmvc.framework.context;

import springmvc.framework.beans.BeanDefinition;
import springmvc.framework.beans.BeanWrapper;
import springmvc.framework.context.support.BeanDefinitionReader;
import springmvc.framework.core.BeanFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author mabailu
 * @Date 2018/4/28 11:05
 * @Description Bean容器
 */
@SuppressWarnings("JavadocReference")
public class ApplicationContext implements BeanFactory {

    private String[] configLocations;

    private BeanDefinitionReader reader;

    //保存Bean配置信息
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
    //保存单列bean
    private Map<String, Object> beanCacheMap = new HashMap<String, Object>();
    //保存被代理过的bean对象
    private Map<String, BeanWrapper> beanWrapperMap = new ConcurrentHashMap<String, BeanWrapper>();

    public ApplicationContext(String... initParameter) {
        this.configLocations = initParameter;
        this.refresh();
    }

    //刷新容器：定位->加载->注册
    private void refresh() {
        //定位
        this.reader = new BeanDefinitionReader(configLocations);

        //加载
        List<String> beanDefinitions = reader.loadBeanDefinitions();

        //注册
        doRegistry(beanDefinitions);

        //依赖注入
        doAutowired();
    }

    private void doAutowired() {
    }

    /**
     * 将{@link BeanDefinition}注册到 {@link beanDefinitionMap}
     *
     * @param beanDefinitions
     */
    private void doRegistry(List<String> beanDefinitions) {

        for (String className : beanDefinitions) {
            try {
                Class<?> beanClass = Class.forName(className);
                if (beanClass.isInterface()) {
                    continue;
                }

                BeanDefinition beanDefinition = reader.registerBean(className);
                if (beanDefinition != null) {
                    this.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
                }

                Class<?>[] interfaces = beanClass.getInterfaces();
                for (Class<?> anInterface : interfaces) {
                    //如果有多个实现类，则会覆盖
                    beanDefinitionMap.put(anInterface.getName(), beanDefinition);
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }

    }


    /**
     * 从这里开始依赖注入，通过读取{@link BeanDefinition} 的信息
     * 通过反射机制创建并返回一个实例
     * Spring不会把原始对象返回，而是会用一个{@link BeanWrapper}进行包装
     * 使用了装饰器模式：保留原有信息，并对其进行扩展和增强
     *
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        return null;
    }
}
