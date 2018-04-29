package springmvc.framework.core;

/**
 * @Author mabailu
 * @Date 2018/4/28 11:06
 * @Description
 */
public interface BeanFactory {

    /**
     * 根据beanName从IOC容器中获得一个实例Bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName);
}
