//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.16 at 11:04:04 AM CET 
//

package org.eclipse.poosl.generatedxmlclasses;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for t_perform_transition_request complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_perform_transition_request">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="handle" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *       &lt;attribute name="data_debug" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_perform_transition_request", propOrder = { "handle" })
public class TPerformTransitionRequest {

    @XmlElement(required = true)
    protected BigInteger handle;

    @XmlAttribute(name = "data_debug")
    protected Boolean dataDebug;

    /**
     * Gets the value of the handle property.
     * 
     * @return possible object is {@link BigInteger }
     * 
     */
    public BigInteger getHandle() {
        return handle;
    }

    /**
     * Sets the value of the handle property.
     * 
     * @param value
     *            allowed object is {@link BigInteger }
     * 
     */
    public void setHandle(BigInteger value) {
        this.handle = value;
    }

    /**
     * Gets the value of the dataDebug property.
     * 
     * @return possible object is {@link Boolean }
     * 
     */
    public Boolean isDataDebug() {
        return dataDebug;
    }

    /**
     * Sets the value of the dataDebug property.
     * 
     * @param value
     *            allowed object is {@link Boolean }
     * 
     */
    public void setDataDebug(Boolean value) {
        this.dataDebug = value;
    }

}
