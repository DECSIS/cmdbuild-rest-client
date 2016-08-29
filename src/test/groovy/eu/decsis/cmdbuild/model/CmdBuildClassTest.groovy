package eu.decsis.cmdbuild.model

import eu.decsis.cmdbuild.rest.CmdBuildRestDatasource
import spock.lang.Specification

class CmdBuildClassTest extends Specification {

    def setup(){
        CmdBuildRestDatasource.configure("http://vm001:8788/cmdbuild/services/rest/v2/","admin","admin")
    }

    def "ResourceName"() {

    }

    def "AddCard"() {

    }

    def "FindCardBy"() {

    }

    def "Get"() {
        given:
        CmdBuildClass expectedResult = new CmdBuildClass(
                _id: "Building",
                defaultOrder: [[attribute:"Name", direction:"ascending"], [attribute:"Complex", direction:"ascending"]],
                description: "Edifício",
                description_attribute_name: "Description",
                name: "Building",
                parent: "Site",
                prototype: false,
        )

        when:
        CmdBuildClass building = CmdBuildClass.getById("Building")

        then:
        building == expectedResult

    }
}
