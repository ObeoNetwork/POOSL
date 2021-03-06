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
 * Java class for t_binary_operation complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_binary_operation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="left_operand" type="{uri:poosl}t_expression"/>
 *         &lt;element name="right_operand" type="{uri:poosl}t_expression"/>
 *       &lt;/sequence>
 *       &lt;attribute name="operator" type="{uri:poosl}t_binary_operator" />
 *       &lt;attribute name="stmt_handle" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_binary_operation", propOrder = { "leftOperand", "rightOperand" })
public class TBinaryOperation {

    @XmlElement(name = "left_operand", required = true)
    protected TExpression leftOperand;

    @XmlElement(name = "right_operand", required = true)
    protected TExpression rightOperand;

    @XmlAttribute(name = "operator")
    protected TBinaryOperator operator;

    @XmlAttribute(name = "stmt_handle")
    protected Integer stmtHandle;

    /**
     * Gets the value of the leftOperand property.
     * 
     * @return possible object is {@link TExpression }
     * 
     */
    public TExpression getLeftOperand() {
        return leftOperand;
    }

    /**
     * Sets the value of the leftOperand property.
     * 
     * @param value
     *            allowed object is {@link TExpression }
     * 
     */
    public void setLeftOperand(TExpression value) {
        this.leftOperand = value;
    }

    /**
     * Gets the value of the rightOperand property.
     * 
     * @return possible object is {@link TExpression }
     * 
     */
    public TExpression getRightOperand() {
        return rightOperand;
    }

    /**
     * Sets the value of the rightOperand property.
     * 
     * @param value
     *            allowed object is {@link TExpression }
     * 
     */
    public void setRightOperand(TExpression value) {
        this.rightOperand = value;
    }

    /**
     * Gets the value of the operator property.
     * 
     * @return possible object is {@link TBinaryOperator }
     * 
     */
    public TBinaryOperator getOperator() {
        return operator;
    }

    /**
     * Sets the value of the operator property.
     * 
     * @param value
     *            allowed object is {@link TBinaryOperator }
     * 
     */
    public void setOperator(TBinaryOperator value) {
        this.operator = value;
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
