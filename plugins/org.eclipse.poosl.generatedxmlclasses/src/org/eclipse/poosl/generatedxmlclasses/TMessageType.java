//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.16 at 11:04:04 AM CET 
//

package org.eclipse.poosl.generatedxmlclasses;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for t_message_type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="t_message_type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="read"/>
 *     &lt;enumeration value="write"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "t_message_type")
@XmlEnum
public enum TMessageType {

    @XmlEnumValue("read")
    READ("read"), @XmlEnumValue("write") //$NON-NLS-1$
    WRITE("write"); //$NON-NLS-1$

    private final String value;

    TMessageType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TMessageType fromValue(String v) {
        for (TMessageType c : TMessageType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}