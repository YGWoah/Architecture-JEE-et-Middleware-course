package ma.enset.IOC;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import ma.enset.IOC.xmlDefinitions.Bean;
import ma.enset.IOC.xmlDefinitions.Beans;

import java.io.File;
import java.util.HashMap;

public class ClassPathXmlApplicationContext {
    private String ConfigFile;
    private Beans beans;
    private HashMap<String,Object> beanMap;
    public ClassPathXmlApplicationContext(String ConfigFile){
        this.ConfigFile = ConfigFile;
        try {
            initializeBeans();
        } catch (JAXBException e) {
            e.printStackTrace();
    }
    }

    private void initializeBeans() throws JAXBException {
        // get the beans from the xml file from the ressources
        File file = new File("src/main/resources/"+ConfigFile);
        // parse the xml file and initialize the beanMap
        JAXBContext context = JAXBContext.newInstance(Beans.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        this.beans = (Beans) unmarshaller.unmarshal(file);

        beanMap = new HashMap<>();
        beans.getBeans().forEach(bean -> {
            try {
                beanMap.put(bean.getId(),Class.forName(bean.getClassName()).getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        populateBeans();


    }

    private void populateBeans(){
        for (Bean bean : beans.getBeans()) {
            Object o = beanMap.get(bean.getId());
            if(bean.getProperties() == null) continue;
            bean.getProperties().forEach(property -> {
                try {
                    Object value = beanMap.get(property.getRef());
                    o.getClass().getDeclaredField(property.getName()).set(o,value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }

    public Object getBean(String beanName){
        return beanMap.get(beanName);
    }
}
