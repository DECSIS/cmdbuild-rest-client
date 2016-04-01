package eu.decsis.cmdbuild.rest

import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import groovy.json.JsonOutput

@Singleton
class CmdBuildRestDatasource extends RestDatasource{

    static {
        Unirest.setDefaultHeader("Content-Type","application/json")
        Unirest.setObjectMapper(new UnirestObjectMapper());
    }

    protected String url
    protected String user
    protected String pass
    private boolean configured = false

    boolean getConfigured() {
        return configured
    }

    static void configure(String url, String user, String pass) {
        this.instance.url = url
        this.instance.user = user
        this.instance.pass = pass
        this.instance.configured = true
    }

    private @Lazy String sessionId = auth();

    private String auth() {
        def resp = Unirest.post("${url}sessions")
            .body([ username: user, password: pass ])
            .asObject(java.lang.Object)
        return resp.body.data._id
    }

    def doGet(String path, queryParameters = null) {
        println "PATH: ${path}"
        HttpResponse<Object> resp = Unirest.get("${url}${path}")
                .header("CMDBuild-Authorization",sessionId)
                .queryString(queryParameters)
                .asObject(java.lang.Object)
        def reqHash = "${path}${queryParameters?:""}".hashCode()
        println "POST ${reqHash}:${JsonOutput.toJson(resp.body)}"
        return resp.body
    }

    def doPost(String path, payload, queryParameters = null) {
        println "PATH: ${path}"
        def resp = Unirest.post("${url}${path}")
                .header("CMDBuild-Authorization",sessionId)
                .queryString(queryParameters)
                .body(payload)
                .asObject(java.lang.Object)
        if( resp.status >= 300 ){
            throw new Exception("POST - ${path} - ${resp.status} ${resp.statusText}, ${payload}, ${resp.body?.error}")
        }
        def reqHash = "${path}${payload?:""}${queryParameters?:""}".hashCode()
        println "POST ${reqHash}:${JsonOutput.toJson(resp.body)}"
        return resp.body
    }

}
