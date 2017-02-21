package eu.decsis.cmdbuild.rest

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class CmdBuildRestDatasourceTest extends Specification {

    @Unroll
    def "CreateUrl"() {
        setup:
        CmdBuildRestDatasource.configure("http://this.is.not/a/valid/url","user","pass")
        def cmdBuildRestDatasource = CmdBuildRestDatasource.instance
        expect:
        cmdBuildRestDatasource.createUrl(url,path) == expect

        where:
        url | path || expect
        "http://cmdbuid/v1" |"instance" ||"http://cmdbuid/v1/instance"
        "http://cmdbuid/v1" |"/instance"||"http://cmdbuid/v1/instance"
        "http://cmdbuid/v1/"|"instance" ||"http://cmdbuid/v1/instance"
        "http://cmdbuid/v1/"|"/instance"||"http://cmdbuid/v1/instance"
    }
}
