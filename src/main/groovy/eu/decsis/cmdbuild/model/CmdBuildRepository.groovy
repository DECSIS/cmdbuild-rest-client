package eu.decsis.cmdbuild.model

import eu.decsis.cmdbuild.CmdBuildApiConfig
import eu.decsis.cmdbuild.model.exception.NotUniqueResultException
import eu.decsis.cmdbuild.rest.RestDatasource
import groovy.json.JsonOutput

trait CmdBuildRepository<T> {

    private static @Lazy RestDatasource restDatasource = CmdBuildApiConfig.restDatasource
    private static Integer DEFAULT_LIST_LIMIT = 100

    String _id

    abstract static String resourceName()

    public static List<T> list(){
        def rawList = restDatasource.doGet("${resourceName()}")?.data
        return rawList as List<T>
    }

    public static T getById(String id){
        def data = restDatasource.doGet("${resourceName()}/${id}")?.data
        return (T) this.newInstance(data)
    }

    public void addChildren(String childrenResourceName, def children) {
        restDatasource.doPost(childPath(childrenResourceName),children)
    }

    public void deleteChildren(String childrenResourceName, def childrenId){
        restDatasource.doDelete("${childPath(childrenResourceName)}/${childrenId}")
    }

    public <C> List<C> listChildren(String childrenResourceName){
        restDatasource.doGet(childPath(childrenResourceName))?.data as List<C>
    }

    public <C> C findChild(String childrenResourceName, Map<String, String> filters, Map<String, String> sortMap){
        Object resp = findChildrenRest(childrenResourceName, filters, 1, sortMap)
        if(resp.meta && resp.meta.total > 1){
            throw new NotUniqueResultException(resp.meta.total)
        }
        return resp.data?resp.data[0]:null as C
    }

    public <C> List<C> findAllChildren(String childrenResourceName, def filters, Integer limit, Map<String,String> sortMap){
        Object resp = findChildrenRest(childrenResourceName, filters, limit, sortMap)
        return resp?.data as List<C>
    }

    private Object findChildrenRest(String childrenResourceName, def filters, Integer limit, Map<String,String> sortMap) {
        def queryParameters = [:]
        queryParameters.filter = toCmdBuildFilterFormat(filters)
        queryParameters.limit = limit ?: DEFAULT_LIST_LIMIT
        if(sortMap) {
            queryParameters.sort = toCmdBuildSort(sortMap)
        }
        def resp = restDatasource.doGet(childPath(childrenResourceName), queryParameters)
        return resp
    }

    private String toCmdBuildSort(Map<String, String> sortMap) {
        def restSort = sortMap.collectEntries { attribute, orderDirection ->
            [("property"): attribute, ("direction"): orderDirection.toUpperCase()?:"ASC"]
        }
        return JsonOutput.toJson([restSort])
    }

    private def toCmdBuildFilterFormat(filters) {
        def simpleFilters = generateSimpleFilters(filters)
        def restFilter
        if (simpleFilters.size() > 1) {
            restFilter = [attribute: [and: simpleFilters]]
        } else {
            restFilter = [attribute: simpleFilters.first()]
        }
        return JsonOutput.toJson(restFilter)
    }

    private static def generateSimpleFilters(filters) {
        def simpleFilters = filters.collect { k, v ->
            [simple: [attribute: k, value: [v], parameterType: "fixed", operator: "equal"]]

        }
        return simpleFilters
    }

    private String childPath(String childrenResourceName) {
        return "${resourceName()}/${get_id()}/${childrenResourceName}"
    }

}
