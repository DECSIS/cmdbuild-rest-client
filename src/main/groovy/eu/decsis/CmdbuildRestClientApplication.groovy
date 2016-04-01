package eu.decsis

import eu.decsis.cmdbuild.CmdBuildRestApiService
import eu.decsis.cmdbuild.model.Card
import eu.decsis.cmdbuild.model.CmdBuildClass
import eu.decsis.cmdbuild.rest.CmdBuildRestDatasource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class CmdbuildRestClientApplication implements CommandLineRunner{

	@Autowired CmdBuildRestApiService restApiService

	static void main(String[] args) {
		SpringApplication.run CmdbuildRestClientApplication, args
	}

	@Override
	void run(String... args) throws Exception {

		CmdBuildRestDatasource.configure("http://localhost:8888/openmaint/services/rest/v1/","admin","admin" )

        CmdBuildClass building = CmdBuildClass.get("Building")
        CmdBuildClass consumption = CmdBuildClass.get("Consumption")
        Card decsisDataCenterCard = building.findCardBy([Code:"0123"])

        def consumptionRead = [
                Cost:320.48,
                EndDate:"2016-03-31T00:00:00",
                InsertionType:67498,
                Invoice:null,
                Item:decsisDataCenterCard._id,
                Quantity:155.0,
                RecordType:67496,
                StartDate:'2016-03-01T00:00:00',
                Type:67500
        ]

        println consumption.addCard(consumptionRead)

        //println CmdBuildClass.get("Consumption").cards
	}
}
