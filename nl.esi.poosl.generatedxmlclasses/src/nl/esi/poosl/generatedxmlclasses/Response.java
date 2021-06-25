//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.16 at 11:04:04 AM CET 
//


package nl.esi.poosl.generatedxmlclasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="load" type="{uri:poosl}t_load_response"/>
 *         &lt;element name="compile" type="{uri:poosl}t_compile_response"/>
 *         &lt;element name="list_files" type="{uri:poosl}t_list_files_response"/>
 *         &lt;element name="get_position" type="{uri:poosl}t_get_position_response"/>
 *         &lt;element name="list_classes" type="{uri:poosl}t_list_classes_response"/>
 *         &lt;element name="get_top_level_specification" type="{uri:poosl}t_get_top_level_specification_response"/>
 *         &lt;element name="instantiate" type="{uri:poosl}t_instantiate_response"/>
 *         &lt;element name="command" type="{uri:poosl}t_command_response"/>
 *         &lt;element name="execution_state" type="{uri:poosl}t_execution_state_change_response"/>
 *         &lt;element name="get_transitions" type="{uri:poosl}t_get_transitions_response"/>
 *         &lt;element name="perform_transition" type="{uri:poosl}t_perform_transition_response"/>
 *         &lt;element name="inspect" type="{uri:poosl}t_inspect_response"/>
 *         &lt;element name="create_breakpoint" type="{uri:poosl}t_create_breakpoint_response"/>
 *         &lt;element name="enable_breakpoint" type="{uri:poosl}t_enable_breakpoint_response"/>
 *         &lt;element name="disable_breakpoint" type="{uri:poosl}t_disable_breakpoint_response"/>
 *         &lt;element name="delete_breakpoint" type="{uri:poosl}t_delete_breakpoint_response"/>
 *         &lt;element name="set_variable" type="{uri:poosl}t_set_variable_response"/>
 *         &lt;element name="eengine_event_setup" type="{uri:poosl}t_eengine_event_setup_response"/>
 *         &lt;element name="communication_event" type="{uri:poosl}t_communication_event"/>
 *         &lt;element name="eengine_event_error" type="{uri:poosl}t_eengine_event_error_response"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "load",
    "compile",
    "listFiles",
    "getPosition",
    "listClasses",
    "getTopLevelSpecification",
    "instantiate",
    "command",
    "executionState",
    "getTransitions",
    "performTransition",
    "inspect",
    "createBreakpoint",
    "enableBreakpoint",
    "disableBreakpoint",
    "deleteBreakpoint",
    "setVariable",
    "eengineEventSetup",
    "communicationEvent",
    "eengineEventError"
})
@XmlRootElement(name = "response")
public class Response {

    protected TLoadResponse load;
    protected TCompileResponse compile;
    @XmlElement(name = "list_files")
    protected TListFilesResponse listFiles;
    @XmlElement(name = "get_position")
    protected TGetPositionResponse getPosition;
    @XmlElement(name = "list_classes")
    protected TListClassesResponse listClasses;
    @XmlElement(name = "get_top_level_specification")
    protected TGetTopLevelSpecificationResponse getTopLevelSpecification;
    protected TInstantiateResponse instantiate;
    protected TCommandResponse command;
    @XmlElement(name = "execution_state")
    protected TExecutionStateChangeResponse executionState;
    @XmlElement(name = "get_transitions")
    protected TGetTransitionsResponse getTransitions;
    @XmlElement(name = "perform_transition")
    protected TPerformTransitionResponse performTransition;
    protected TInspectResponse inspect;
    @XmlElement(name = "create_breakpoint")
    protected TCreateBreakpointResponse createBreakpoint;
    @XmlElement(name = "enable_breakpoint")
    protected TEnableBreakpointResponse enableBreakpoint;
    @XmlElement(name = "disable_breakpoint")
    protected TDisableBreakpointResponse disableBreakpoint;
    @XmlElement(name = "delete_breakpoint")
    protected TDeleteBreakpointResponse deleteBreakpoint;
    @XmlElement(name = "set_variable")
    protected TSetVariableResponse setVariable;
    @XmlElement(name = "eengine_event_setup")
    protected TEengineEventSetupResponse eengineEventSetup;
    @XmlElement(name = "communication_event")
    protected TCommunicationEvent communicationEvent;
    @XmlElement(name = "eengine_event_error")
    protected TEengineEventErrorResponse eengineEventError;

    /**
     * Gets the value of the load property.
     * 
     * @return
     *     possible object is
     *     {@link TLoadResponse }
     *     
     */
    public TLoadResponse getLoad() {
        return load;
    }

    /**
     * Sets the value of the load property.
     * 
     * @param value
     *     allowed object is
     *     {@link TLoadResponse }
     *     
     */
    public void setLoad(TLoadResponse value) {
        this.load = value;
    }

    /**
     * Gets the value of the compile property.
     * 
     * @return
     *     possible object is
     *     {@link TCompileResponse }
     *     
     */
    public TCompileResponse getCompile() {
        return compile;
    }

    /**
     * Sets the value of the compile property.
     * 
     * @param value
     *     allowed object is
     *     {@link TCompileResponse }
     *     
     */
    public void setCompile(TCompileResponse value) {
        this.compile = value;
    }

    /**
     * Gets the value of the listFiles property.
     * 
     * @return
     *     possible object is
     *     {@link TListFilesResponse }
     *     
     */
    public TListFilesResponse getListFiles() {
        return listFiles;
    }

    /**
     * Sets the value of the listFiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link TListFilesResponse }
     *     
     */
    public void setListFiles(TListFilesResponse value) {
        this.listFiles = value;
    }

    /**
     * Gets the value of the getPosition property.
     * 
     * @return
     *     possible object is
     *     {@link TGetPositionResponse }
     *     
     */
    public TGetPositionResponse getGetPosition() {
        return getPosition;
    }

    /**
     * Sets the value of the getPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link TGetPositionResponse }
     *     
     */
    public void setGetPosition(TGetPositionResponse value) {
        this.getPosition = value;
    }

    /**
     * Gets the value of the listClasses property.
     * 
     * @return
     *     possible object is
     *     {@link TListClassesResponse }
     *     
     */
    public TListClassesResponse getListClasses() {
        return listClasses;
    }

    /**
     * Sets the value of the listClasses property.
     * 
     * @param value
     *     allowed object is
     *     {@link TListClassesResponse }
     *     
     */
    public void setListClasses(TListClassesResponse value) {
        this.listClasses = value;
    }

    /**
     * Gets the value of the getTopLevelSpecification property.
     * 
     * @return
     *     possible object is
     *     {@link TGetTopLevelSpecificationResponse }
     *     
     */
    public TGetTopLevelSpecificationResponse getGetTopLevelSpecification() {
        return getTopLevelSpecification;
    }

    /**
     * Sets the value of the getTopLevelSpecification property.
     * 
     * @param value
     *     allowed object is
     *     {@link TGetTopLevelSpecificationResponse }
     *     
     */
    public void setGetTopLevelSpecification(TGetTopLevelSpecificationResponse value) {
        this.getTopLevelSpecification = value;
    }

    /**
     * Gets the value of the instantiate property.
     * 
     * @return
     *     possible object is
     *     {@link TInstantiateResponse }
     *     
     */
    public TInstantiateResponse getInstantiate() {
        return instantiate;
    }

    /**
     * Sets the value of the instantiate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TInstantiateResponse }
     *     
     */
    public void setInstantiate(TInstantiateResponse value) {
        this.instantiate = value;
    }

    /**
     * Gets the value of the command property.
     * 
     * @return
     *     possible object is
     *     {@link TCommandResponse }
     *     
     */
    public TCommandResponse getCommand() {
        return command;
    }

    /**
     * Sets the value of the command property.
     * 
     * @param value
     *     allowed object is
     *     {@link TCommandResponse }
     *     
     */
    public void setCommand(TCommandResponse value) {
        this.command = value;
    }

    /**
     * Gets the value of the executionState property.
     * 
     * @return
     *     possible object is
     *     {@link TExecutionStateChangeResponse }
     *     
     */
    public TExecutionStateChangeResponse getExecutionState() {
        return executionState;
    }

    /**
     * Sets the value of the executionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link TExecutionStateChangeResponse }
     *     
     */
    public void setExecutionState(TExecutionStateChangeResponse value) {
        this.executionState = value;
    }

    /**
     * Gets the value of the getTransitions property.
     * 
     * @return
     *     possible object is
     *     {@link TGetTransitionsResponse }
     *     
     */
    public TGetTransitionsResponse getGetTransitions() {
        return getTransitions;
    }

    /**
     * Sets the value of the getTransitions property.
     * 
     * @param value
     *     allowed object is
     *     {@link TGetTransitionsResponse }
     *     
     */
    public void setGetTransitions(TGetTransitionsResponse value) {
        this.getTransitions = value;
    }

    /**
     * Gets the value of the performTransition property.
     * 
     * @return
     *     possible object is
     *     {@link TPerformTransitionResponse }
     *     
     */
    public TPerformTransitionResponse getPerformTransition() {
        return performTransition;
    }

    /**
     * Sets the value of the performTransition property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPerformTransitionResponse }
     *     
     */
    public void setPerformTransition(TPerformTransitionResponse value) {
        this.performTransition = value;
    }

    /**
     * Gets the value of the inspect property.
     * 
     * @return
     *     possible object is
     *     {@link TInspectResponse }
     *     
     */
    public TInspectResponse getInspect() {
        return inspect;
    }

    /**
     * Sets the value of the inspect property.
     * 
     * @param value
     *     allowed object is
     *     {@link TInspectResponse }
     *     
     */
    public void setInspect(TInspectResponse value) {
        this.inspect = value;
    }

    /**
     * Gets the value of the createBreakpoint property.
     * 
     * @return
     *     possible object is
     *     {@link TCreateBreakpointResponse }
     *     
     */
    public TCreateBreakpointResponse getCreateBreakpoint() {
        return createBreakpoint;
    }

    /**
     * Sets the value of the createBreakpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link TCreateBreakpointResponse }
     *     
     */
    public void setCreateBreakpoint(TCreateBreakpointResponse value) {
        this.createBreakpoint = value;
    }

    /**
     * Gets the value of the enableBreakpoint property.
     * 
     * @return
     *     possible object is
     *     {@link TEnableBreakpointResponse }
     *     
     */
    public TEnableBreakpointResponse getEnableBreakpoint() {
        return enableBreakpoint;
    }

    /**
     * Sets the value of the enableBreakpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link TEnableBreakpointResponse }
     *     
     */
    public void setEnableBreakpoint(TEnableBreakpointResponse value) {
        this.enableBreakpoint = value;
    }

    /**
     * Gets the value of the disableBreakpoint property.
     * 
     * @return
     *     possible object is
     *     {@link TDisableBreakpointResponse }
     *     
     */
    public TDisableBreakpointResponse getDisableBreakpoint() {
        return disableBreakpoint;
    }

    /**
     * Sets the value of the disableBreakpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDisableBreakpointResponse }
     *     
     */
    public void setDisableBreakpoint(TDisableBreakpointResponse value) {
        this.disableBreakpoint = value;
    }

    /**
     * Gets the value of the deleteBreakpoint property.
     * 
     * @return
     *     possible object is
     *     {@link TDeleteBreakpointResponse }
     *     
     */
    public TDeleteBreakpointResponse getDeleteBreakpoint() {
        return deleteBreakpoint;
    }

    /**
     * Sets the value of the deleteBreakpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDeleteBreakpointResponse }
     *     
     */
    public void setDeleteBreakpoint(TDeleteBreakpointResponse value) {
        this.deleteBreakpoint = value;
    }

    /**
     * Gets the value of the setVariable property.
     * 
     * @return
     *     possible object is
     *     {@link TSetVariableResponse }
     *     
     */
    public TSetVariableResponse getSetVariable() {
        return setVariable;
    }

    /**
     * Sets the value of the setVariable property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSetVariableResponse }
     *     
     */
    public void setSetVariable(TSetVariableResponse value) {
        this.setVariable = value;
    }

    /**
     * Gets the value of the eengineEventSetup property.
     * 
     * @return
     *     possible object is
     *     {@link TEengineEventSetupResponse }
     *     
     */
    public TEengineEventSetupResponse getEengineEventSetup() {
        return eengineEventSetup;
    }

    /**
     * Sets the value of the eengineEventSetup property.
     * 
     * @param value
     *     allowed object is
     *     {@link TEengineEventSetupResponse }
     *     
     */
    public void setEengineEventSetup(TEengineEventSetupResponse value) {
        this.eengineEventSetup = value;
    }

    /**
     * Gets the value of the communicationEvent property.
     * 
     * @return
     *     possible object is
     *     {@link TCommunicationEvent }
     *     
     */
    public TCommunicationEvent getCommunicationEvent() {
        return communicationEvent;
    }

    /**
     * Sets the value of the communicationEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link TCommunicationEvent }
     *     
     */
    public void setCommunicationEvent(TCommunicationEvent value) {
        this.communicationEvent = value;
    }

    /**
     * Gets the value of the eengineEventError property.
     * 
     * @return
     *     possible object is
     *     {@link TEengineEventErrorResponse }
     *     
     */
    public TEengineEventErrorResponse getEengineEventError() {
        return eengineEventError;
    }

    /**
     * Sets the value of the eengineEventError property.
     * 
     * @param value
     *     allowed object is
     *     {@link TEengineEventErrorResponse }
     *     
     */
    public void setEengineEventError(TEengineEventErrorResponse value) {
        this.eengineEventError = value;
    }

}
