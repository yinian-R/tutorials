package com.wymm.greeter.autoconfigure;

import com.wymm.greeter.library.GreeterConfigParams;
import com.wymm.greeter.library.GreeterTemplate;
import com.wymm.greeter.library.GreetingConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.ClassUtils;

@Slf4j
public class GreeterBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        boolean hasClass = ClassUtils.isPresent("com.wymm.greeter.library.GreeterTemplate", GreeterBeanFactoryPostProcessor.class.getClassLoader());
        if (!hasClass) {
            log.warn("GreeterTemplate is not present in classpath");
            return;
        }
        if (beanFactory.containsBean("greeterTemplate")) {
            log.info("greeterTemplate is registered");
        }
        
        register(beanFactory);
    }
    
    private void register(ConfigurableListableBeanFactory beanFactory) {
        log.info("register greeterTemplate");
        GreetingConfig greetingConfig;
        if (beanFactory.containsBean("greetingConfig")) {
            greetingConfig = (GreetingConfig) beanFactory.getBean("greetingConfig");
        } else {
            greetingConfig = new GreetingConfig();
            greetingConfig.put(GreeterConfigParams.USER_NAME, "user");
            greetingConfig.put(GreeterConfigParams.NIGHT_MESSAGE, "good night");
        }
        
        if (beanFactory instanceof BeanDefinitionRegistry) {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(GreeterTemplate.class);
            ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
            constructorArgumentValues.addGenericArgumentValue(greetingConfig);
            beanDefinition.setConstructorArgumentValues(constructorArgumentValues);
            
            ((BeanDefinitionRegistry) beanFactory).registerBeanDefinition("greeterTemplate", beanDefinition);
        } else {
            beanFactory.registerSingleton("greeterTemplate", new GreeterTemplate(greetingConfig));
        }
        
        
    }
}
