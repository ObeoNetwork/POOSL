//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.16 at 11:04:04 AM CET 
//


package nl.esi.poosl.generatedxmlclasses;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_receiver_info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_receiver_info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="process_path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="handle" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="port" type="{uri:poosl}t_port_name"/>
 *         &lt;element name="message" type="{uri:poosl}t_message_name"/>
 *         &lt;element name="parameter_count" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="node" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_receiver_info", propOrder = {
    "processPath",
    "handle",
    "port",
    "message",
    "parameterCount",
    "node"
})
public class TReceiverInfo {

    @XmlElement(name = "process_path", required = true)
    protected String processPath;
    @XmlElement(required = true)
    protected BigInteger handle;
    @XmlElement(required = true)
    protected String port;
    @XmlElement(required = true)
    protected String message;
    @XmlElement(name = "parameter_count", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger parameterCount;
    @XmlElement(required = true)
    protected BigInteger node;

    /**
     * Gets the value of the processPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessPath() {
        return processPath;
    }

    /**
     * Sets the value of the processPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessPath(String value) {
        this.processPath = value;
    }

    /**
     * Gets the value of the handle property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHandle() {
        return handle;
    }

    /**
     * Sets the value of the handle property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHandle(BigInteger value) {
        this.handle = value;
    }

    /**
     * Gets the value of the port property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPort() {
        return port;
    }

    /**
     * Sets the value of the port property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPort(String value) {
        this.port = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the parameterCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getParameterCount() {
        return parameterCount;
    }

    /**
     * Sets the value of the parameterCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setParameterCount(BigInteger value) {
        this.parameterCount = value;
    }

    /**
     * Gets the value of the node property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNode() {
        return node;
    }

    /**
     * Sets the value of the node property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNode(BigInteger value) {
        this.node = value;
    }

}
