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
        given:
        def card = [:]
        card.Code = "DELETEME"
        card.Description = "Used for testing purposes"

        when:
        CmdBuildClass.getById("not_identified").addCard(card)
        card = CmdBuildClass.getById("not_identified").findCardBy(["Code":"DELETEME"])

        then:
        card

        cleanup:
        CmdBuildClass.getById("not_identified").deleteCard(card)
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
