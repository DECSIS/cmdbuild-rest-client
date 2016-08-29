package eu.decsis.cmdbuild.model

import groovy.transform.EqualsAndHashCode
import sun.reflect.generics.reflectiveObjects.NotImplementedException

@EqualsAndHashCode()
class LookupType implements CmdBuildRepository<LookupType>{

    String _id
    String name
    String parent

    static String resourceName() { return "lookup_types" }

    List<LookupTypeValue> listValues(){
        return this.<LookupTypeValue>listChildren("values")
    }

    @Override
    def <C> List<C> findAllChildren(String childrenResourceName, Object filters, Integer limit, Map<String, String> sortMap) {
        throw new NotImplementedException()
    }

    @Override
    def <C> C findChild(String childrenResourceName, Map<String, String> filters, Map<String, String> sortMap) {
        throw new NotImplementedException()
    }
}
