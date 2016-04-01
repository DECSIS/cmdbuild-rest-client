package eu.decsis.cmdbuild

import eu.decsis.cmdbuild.rest.CmdBuildRestDatasource
import eu.decsis.cmdbuild.rest.RestDatasource
import spock.lang.Specification

class CmdBuildRestClientTests extends Specification{

    def "rest client is a singleton" () {
        expect:
            CmdBuildRestDatasource.instance instanceof RestDatasource
    }

    def "rest client not configured" () {
        expect:
            !CmdBuildRestDatasource.instance.configured
    }

    def "rest client configured" () {
        when:
            CmdBuildRestDatasource.configure("http://this.is.not/a/valid/url","user","pass")
        then:
            CmdBuildRestDatasource.instance.configured
    }

}
