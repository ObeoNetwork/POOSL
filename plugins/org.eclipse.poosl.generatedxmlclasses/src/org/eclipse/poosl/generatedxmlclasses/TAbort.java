//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.16 at 11:04:04 AM CET 
//

package org.eclipse.poosl.generatedxmlclasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for t_abort complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_abort">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="normal_behavior" type="{uri:poosl}t_statement"/>
 *         &lt;element name="aborting_behavior" type="{uri:poosl}t_statement"/>
 *       &lt;/sequence>
 *       &lt;attribute name="stmt_handle" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_abort", propOrder = { "normalBehavior", "abortingBehavior" })
public class TAbort {

    @XmlElement(name = "normal_behavior", required = true)
    protected TStatement normalBehavior;

    @XmlElement(name = "aborting_behavior", required = true)
    protected TStatement abortingBehavior;

    @XmlAttribute(name = "stmt_handle")
    protected Integer stmtHandle;

    /**
     * Gets the value of the normalBehavior property.
     * 
     * @return possible object is {@link TStatement }
     * 
     */
    public TStatement getNormalBehavior() {
        return normalBehavior;
    }

    /**
     * Sets the value of the normalBehavior property.
     * 
     * @param value
     *            allowed object is {@link TStatement }
     * 
     */
    public void setNormalBehavior(TStatement value) {
        this.normalBehavior = value;
    }

    /**
     * Gets the value of the abortingBehavior property.
     * 
     * @return possible object is {@link TStatement }
     * 
     */
    public TStatement getAbortingBehavior() {
        return abortingBehavior;
    }

    /**
     * Sets the value of the abortingBehavior property.
     * 
     * @param value
     *            allowed object is {@link TStatement }
     * 
     */
    public void setAbortingBehavior(TStatement value) {
        this.abortingBehavior = value;
    }

    /**
     * Gets the value of the stmtHandle property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getStmtHandle() {
        return stmtHandle;
    }

    /**
     * Sets the value of the stmtHandle property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setStmtHandle(Integer value) {
        this.stmtHandle = value;
    }

}
