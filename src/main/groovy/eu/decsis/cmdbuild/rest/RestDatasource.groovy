package eu.decsis.cmdbuild.rest

abstract class RestDatasource {

    abstract def doGet(String path, queryParameters)
    abstract def doPost(String path, payload, queryParameters)
    abstract def doDelete(String path, queryParameters)
}
