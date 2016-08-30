package eu.decsis.cmdbuild

import eu.decsis.cmdbuild.model.Card
import eu.decsis.cmdbuild.model.CmdBuildClass
import eu.decsis.cmdbuild.rest.CmdBuildRestDatasource
import spock.lang.Specification

class Sandbox extends Specification {

    def "sandbox"() {
        expect:
        CmdBuildRestDatasource.configure("http://vm003:8789/cmdbuild/services/rest/v2/", "admin", "admin")

        CmdBuildClass building = CmdBuildClass.getById("Building")
        CmdBuildClass consumption = CmdBuildClass.getById("Consumption")
        Card decsisDataCenterCard = building.findCardBy([Code: "0123"])

        def consumptionRead = [
                Cost         : 320.48,
                EndDate      : "2016-03-31T00:00:00",
                InsertionType: 67498,
                Invoice      : null,
                Item         : 33,
                Quantity     : 155.0,
                RecordType   : 67496,
                StartDate    : '2016-03-01T00:00:00',
                Type         : 67500
        ]

        consumption.addCard(consumptionRead)
    }
}