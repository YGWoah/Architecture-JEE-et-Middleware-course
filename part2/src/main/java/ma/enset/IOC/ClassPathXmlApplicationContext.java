package ma.enset.IOC;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import ma.enset.IOC.xmlDefinitions.Bean;
import ma.enset.IOC.xmlDefinitions.Beans;
import ma.enset.IOC.xmlDefinitions.ConstructorArg;

import javax.imageio.plugins.tiff.ExifParentTIFFTagSet;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.HashMap;

public class ClassPathXmlApplicationContext {
    private String ConfigFile;
    private Beans beans;
    private HashMap<String, Object> beanMap;

    public ClassPathXmlApplicationContext(String ConfigFile) throws JAXBException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        this.ConfigFile = ConfigFile;
        initializeBeans();
    }

    private void initializeBeans() throws JAXBException {
        this.beans = getBeans(this.ConfigFile);
        this.beanMap = new HashMap<>();

        createBeansInstance();
        populateBeans();

    }

    private Beans getBeans(String configFileString) throws JAXBException {
        File configFile = new File("src/main/resources/" + configFileString);
        JAXBContext context = JAXBContext.newInstance(Beans.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Beans) unmarshaller.unmarshal(configFile);
    }

    private void createBeansInstance() {
        this.beans.getBeans().forEach(bean -> {
            try {
                Object classObject;
                if (bean.getConstructorArgs() != null) {
                    classObject = createBeanInstanceWithArgs(bean);
                } else classObject = Class.forName(bean.getClassName()).getDeclaredConstructor().newInstance();

                beanMap.put(bean.getId(), classObject);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Object createBeanInstanceWithArgs(Bean bean) throws Exception {
        ConstructorArg[] constructorArgs = bean.getConstructorArgs().toArray(new ConstructorArg[0]);
        Object[] constructorArgObjects = new Object[constructorArgs.length];

        for (int i = 0; i < constructorArgs.length; i++) {
            constructorArgObjects[i] = beanMap.get(constructorArgs[i].getRef());
        }

        Class<?>[] argTypes = new Class[constructorArgs.length];
        for (int i = 0; i < constructorArgs.length; i++) {
            argTypes[i] = constructorArgObjects[i].getClass();
        }

        Constructor<?> constructor = Class.forName(bean.getClassName()).getDeclaredConstructor(argTypes);

        return constructor.newInstance(constructorArgObjects);
    }

    private void populateBeans() {
        for (Bean bean : beans.getBeans()) {
            Object o = beanMap.get(bean.getId());
            if (bean.getProperties() == null) continue;
            populateBean(bean, o);
        }

    }

    private void populateBean(Bean bean, Object o) {
        bean.getProperties().forEach(property -> {
            try {
                Object value = beanMap.get(property.getRef());
                o.getClass().getDeclaredField(property.getName()).set(o, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }
}
