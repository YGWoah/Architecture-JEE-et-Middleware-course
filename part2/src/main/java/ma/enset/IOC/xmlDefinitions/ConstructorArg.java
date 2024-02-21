package ma.enset.IOC.xmlDefinitions;

import jakarta.xml.bind.annotation.XmlAttribute;

public class ConstructorArg {

    private String ref;

    @XmlAttribute
    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
