package eu.decsis.cmdbuild.model

import groovy.transform.EqualsAndHashCode
import sun.reflect.generics.reflectiveObjects.NotImplementedException

@EqualsAndHashCode
class Process implements CmdBuildRepository<Process>{

    String _id
    def dynamicProperties= [:]


    public String getInstancesResouce() {
        ProcessInstance.resourceName().replace("/", "")
    }

    static String resourceName() { return "/processes" }

    def propertyMissing(String name, value) { dynamicProperties[name] = value }
    def propertyMissing(String name) { dynamicProperties= [name] }

    List<ProcessInstance> getProcessInstance(){
        return this.<ProcessInstance>listChildren(getInstancesResouce())
    }
    List<Attribute> getAttributes(){
        return this.<Attribute>listChildren("attributes")
    }

    void addProcessInstance(instance) {
        throw new NotImplementedException()
    }

    void deleteInstance(ProcessInstance instance){
        throw new NotImplementedException()
    }

    private void printMandatoryAttributes(){
        throw new NotImplementedException()
    }

    ProcessInstance findInstanceBy(Map<String, String> filters, Map<String, String> order = null) {
        this.<Card>findChild(getInstancesResouce(),filters, order)
    }

    List<ProcessInstance> findAllInstancesBy(Map<String, String> filters, Integer limit, Map<String, String> sortMap = null) {
        this.<Card>findAllChildren(getInstancesResouce(),filters, limit, sortMap)
    }


    List<ProcessInstance> listChildren(){
        this.<ProcessInstance>listChildren(getInstancesResouce())
    }

}
