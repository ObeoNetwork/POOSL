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
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for t_transition complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_transition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="communication" type="{uri:poosl}t_communication_transition"/>
 *         &lt;element name="process_step" type="{uri:poosl}t_process_step_transition"/>
 *         &lt;element name="data_step" type="{uri:poosl}t_data_step_transition"/>
 *         &lt;element name="delay" type="{uri:poosl}t_delay_transition"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_transition", propOrder = { "communication", "processStep", "dataStep", "delay" })
public class TTransition {

    protected TCommunicationTransition communication;

    @XmlElement(name = "process_step")
    protected TProcessStepTransition processStep;

    @XmlElement(name = "data_step")
    protected TDataStepTransition dataStep;

    protected TDelayTransition delay;

    /**
     * Gets the value of the communication property.
     * 
     * @return possible object is {@link TCommunicationTransition }
     * 
     */
    public TCommunicationTransition getCommunication() {
        return communication;
    }

    /**
     * Sets the value of the communication property.
     * 
     * @param value
     *            allowed object is {@link TCommunicationTransition }
     * 
     */
    public void setCommunication(TCommunicationTransition value) {
        this.communication = value;
    }

    /**
     * Gets the value of the processStep property.
     * 
     * @return possible object is {@link TProcessStepTransition }
     * 
     */
    public TProcessStepTransition getProcessStep() {
        return processStep;
    }

    /**
     * Sets the value of the processStep property.
     * 
     * @param value
     *            allowed object is {@link TProcessStepTransition }
     * 
     */
    public void setProcessStep(TProcessStepTransition value) {
        this.processStep = value;
    }

    /**
     * Gets the value of the dataStep property.
     * 
     * @return possible object is {@link TDataStepTransition }
     * 
     */
    public TDataStepTransition getDataStep() {
        return dataStep;
    }

    /**
     * Sets the value of the dataStep property.
     * 
     * @param value
     *            allowed object is {@link TDataStepTransition }
     * 
     */
    public void setDataStep(TDataStepTransition value) {
        this.dataStep = value;
    }

    /**
     * Gets the value of the delay property.
     * 
     * @return possible object is {@link TDelayTransition }
     * 
     */
    public TDelayTransition getDelay() {
        return delay;
    }

    /**
     * Sets the value of the delay property.
     * 
     * @param value
     *            allowed object is {@link TDelayTransition }
     * 
     */
    public void setDelay(TDelayTransition value) {
        this.delay = value;
    }

}