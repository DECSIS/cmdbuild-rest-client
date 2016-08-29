package eu.decsis.cmdbuild.model

import eu.decsis.cmdbuild.rest.CmdBuildRestDatasource
import spock.lang.Specification

class LookupTypeTest extends Specification {

    def setup(){
        CmdBuildRestDatasource.configure("http://vm001:8788/cmdbuild/services/rest/v2/","admin","admin")
    }

    def "List"() {
        when:
        def res = LookupType.list()

        then:
        res.size() == 88
        res[0] == [_id:"ActivityCategory", name:"ActivityCategory", parent:null]

    }

    def "GetById"() {
        when:
        LookupType res = LookupType.getById("ActivityCategory")

        then:
        res == new LookupType(_id: "ActivityCategory",name: "ActivityCategory")

    }

    def "ListValues"() {
        when:
        LookupType lu = LookupType.getById("ActivityCategory")
        List<LookupTypeValue> res = lu.listValues()
        LookupTypeValue luv = new LookupTypeValue(_id:"94", _type:"ActivityCategory", active:true, code:"P", description:"Preventiva", number:1, parent_id:null, parent_type:null)
        luv.setDefault(false)
        LookupTypeValue resLuv = res[0]

        then:
        res.size() == 3
        resLuv == luv

    }

    def "Get_id"() {
        when:
        LookupType lu = LookupType.getById("ActivityCategory")

        then:
        lu.get_id() == "ActivityCategory"

    }

}
