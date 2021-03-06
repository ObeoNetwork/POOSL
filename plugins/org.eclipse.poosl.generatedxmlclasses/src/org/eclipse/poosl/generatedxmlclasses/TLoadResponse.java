//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.16 at 11:04:04 AM CET 
//

package org.eclipse.poosl.generatedxmlclasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for t_load_response complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_load_response">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="result" type="{uri:poosl}t_file_io_result"/>
 *         &lt;element name="error" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contents" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_load_response", propOrder = { "result", "error", "contents" })
public class TLoadResponse {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected TFileIoResult result;

    @XmlElement(required = true)
    protected String error;

    @XmlElement(required = true)
    protected String contents;

    /**
     * Gets the value of the result property.
     * 
     * @return possible object is {@link TFileIoResult }
     * 
     */
    public TFileIoResult getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *            allowed object is {@link TFileIoResult }
     * 
     */
    public void setResult(TFileIoResult value) {
        this.result = value;
    }

    /**
     * Gets the value of the error property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setError(String value) {
        this.error = value;
    }

    /**
     * Gets the value of the contents property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getContents() {
        return contents;
    }

    /**
     * Sets the value of the contents property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setContents(String value) {
        this.contents = value;
    }

}
