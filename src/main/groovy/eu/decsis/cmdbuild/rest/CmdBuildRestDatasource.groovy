package eu.decsis.cmdbuild.rest

import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import groovy.util.logging.Slf4j

@Singleton
@Slf4j
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

    private String sessionId

    String getSessionId(){
        if(!sessionId){
            sessionId = auth()
        }
        return sessionId
    }

    private String auth() {
        def resp = Unirest.post(createUrl(url,"/sessions"))
            .body([ username: user, password: pass ])
            .asObject(java.lang.Object)
        String sid = resp.body.data._id
        log.info("New session  - ${sid}")
        return sid
    }


    def doGet(String path, queryParameters = null) {
        HttpResponse<Object> resp = Unirest.get(createUrl(url,path))
                .header("CMDBuild-Authorization",getSessionId())
                .queryString(queryParameters)
                .asObject(java.lang.Object)
        if( resp.status == 401 ){
            sessionId = null
            doGet(path,queryParameters)
        }
        if( resp.status >= 300 ){
            throw new Exception("GET - ${path} - ${resp.status} ${resp.statusText}, ${getErrorMessage(resp)}")
        }
        log.info("GET $path $queryParameters")
        return resp.body
    }



    def doPost(String path, payload, queryParameters = null) {
        def resp = Unirest.post(createUrl(url,path))
                .header("CMDBuild-Authorization",getSessionId())
                .queryString(queryParameters)
                .body(payload)
                .asObject(java.lang.Object)
        if( resp.status == 401 ){
            sessionId = null
            doPost(path,payload,queryParameters)
        }
        if( resp.status >= 300 ){
            throw new Exception("POST - ${path} - ${resp.status} ${resp.statusText}, ${payload}, ${getErrorMessage(resp)}")
        }
        log.info("POST $path $payload $queryParameters")
        return resp.body
    }

    def doPut(String path, payload, queryParameters = null) {
        def resp = Unirest.put(createUrl(url,path))
                .header("CMDBuild-Authorization",getSessionId())
                .queryString(queryParameters)
                .body(payload)
                .asObject(java.lang.Object)
        if( resp.status == 401 ){
            sessionId = null
            doPut(path,payload,queryParameters)
        }
        if( resp.status >= 300 ){
            throw new Exception("PUT - ${path} - ${resp.status} ${resp.statusText}, ${payload}, ${getErrorMessage(resp)}")
        }
        log.info("PUT $path $payload $queryParameters")
        return resp.body
    }

    @Override
    def doDelete(String path, queryParameters = null) {
        def resp = Unirest.delete(createUrl(url,path))
                .header("CMDBuild-Authorization",getSessionId())
                .queryString(queryParameters)
                .asObject(java.lang.Object)
        if( resp.status == 401 ){
            sessionId = null
            doDelete(path,queryParameters)
        }
        if( resp.status >= 300 ){

            throw new Exception("DELETE - ${path} - ${resp.status} ${resp.statusText}, ${getErrorMessage(resp)}")
        }
        log.info("DELETE $path $queryParameters")
        return resp.body
    }

    private static getErrorMessage(resp){

        if(resp.body?.error == "not in json format"){
            return resp.body?.detail
        }
    }

    String createUrl(String url, String path) {
        if(url.endsWith("/") && path.startsWith("/")){
            return "${url[0..-2]}${path}"
        }else if(!url.endsWith("/") && !path.startsWith("/")){
            return "${url}/${path}"
        }

        return "${url}${path}"
    }
}
