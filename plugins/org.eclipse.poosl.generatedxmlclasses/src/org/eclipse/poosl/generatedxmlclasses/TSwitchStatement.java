//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.16 at 11:04:04 AM CET 
//

package org.eclipse.poosl.generatedxmlclasses;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for t_switch_statement complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_switch_statement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="control_expression" type="{uri:poosl}t_expression"/>
 *         &lt;element name="case" type="{uri:poosl}t_switch_case_statement" maxOccurs="unbounded"/>
 *         &lt;element name="default" type="{uri:poosl}t_statement" minOccurs="0"/>
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
@XmlType(name = "t_switch_statement", propOrder = { "controlExpression", "_case", "_default" })
public class TSwitchStatement {

    @XmlElement(name = "control_expression", required = true)
    protected TExpression controlExpression;

    @XmlElement(name = "case", required = true)
    protected List<TSwitchCaseStatement> _case;

    @XmlElement(name = "default")
    protected TStatement _default;

    @XmlAttribute(name = "stmt_handle")
    protected Integer stmtHandle;

    /**
     * Gets the value of the controlExpression property.
     * 
     * @return possible object is {@link TExpression }
     * 
     */
    public TExpression getControlExpression() {
        return controlExpression;
    }

    /**
     * Sets the value of the controlExpression property.
     * 
     * @param value
     *            allowed object is {@link TExpression }
     * 
     */
    public void setControlExpression(TExpression value) {
        this.controlExpression = value;
    }

    /**
     * Gets the value of the case property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the case property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getCase().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TSwitchCaseStatement }
     * 
     * 
     */
    public List<TSwitchCaseStatement> getCase() {
        if (_case == null) {
            _case = new ArrayList<TSwitchCaseStatement>();
        }
        return this._case;
    }

    /**
     * Gets the value of the default property.
     * 
     * @return possible object is {@link TStatement }
     * 
     */
    public TStatement getDefault() {
        return _default;
    }

    /**
     * Sets the value of the default property.
     * 
     * @param value
     *            allowed object is {@link TStatement }
     * 
     */
    public void setDefault(TStatement value) {
        this._default = value;
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
