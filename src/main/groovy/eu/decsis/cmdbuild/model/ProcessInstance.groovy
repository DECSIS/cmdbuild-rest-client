package eu.decsis.cmdbuild.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class ProcessInstance implements CmdBuildRepository<ProcessInstance>{

    String _id
    String _type
    def dynamicProperties= [:]
    static String resourceName() { return "/instances" }

    def propertyMissing(String name, value) { dynamicProperties[name] = value }
    def propertyMissing(String name) { dynamicProperties= [name] }

}
