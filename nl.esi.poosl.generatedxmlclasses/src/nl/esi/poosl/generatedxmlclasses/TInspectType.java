//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.16 at 11:04:04 AM CET 
//


package nl.esi.poosl.generatedxmlclasses;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_inspect_type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="t_inspect_type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="process"/>
 *     &lt;enumeration value="cluster"/>
 *     &lt;enumeration value="channel"/>
 *     &lt;enumeration value="model"/>
 *     &lt;enumeration value="system"/>
 *     &lt;enumeration value="channel"/>
 *     &lt;enumeration value="port"/>
 *     &lt;enumeration value="variable"/>
 *     &lt;enumeration value="data"/>
 *     &lt;enumeration value="stackframe"/>
 *     &lt;enumeration value="variable_context"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "t_inspect_type")
@XmlEnum
public enum TInspectType {

    @XmlEnumValue("process")
    PROCESS("process"),
    @XmlEnumValue("cluster")
    CLUSTER("cluster"),
    @XmlEnumValue("channel")
    CHANNEL("channel"),
    @XmlEnumValue("model")
    MODEL("model"),
    @XmlEnumValue("system")
    SYSTEM("system"),
    @XmlEnumValue("port")
    PORT("port"),
    @XmlEnumValue("variable")
    VARIABLE("variable"),
    @XmlEnumValue("data")
    DATA("data"),
    @XmlEnumValue("stackframe")
    STACKFRAME("stackframe"),
    @XmlEnumValue("variable_context")
    VARIABLE_CONTEXT("variable_context");
    private final String value;

    TInspectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TInspectType fromValue(String v) {
        for (TInspectType c: TInspectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
