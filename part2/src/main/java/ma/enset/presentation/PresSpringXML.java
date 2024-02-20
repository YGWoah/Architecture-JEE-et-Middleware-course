package ma.enset.presentation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.metrics.ApplicationStartup;
public class PresSpringXML {
    public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    ma.enset.Metier.IMetier metier = (ma.enset.Metier.IMetier) context.getBean("metier");
    System.out.println("resultat => "+metier.caculate());
}
}
