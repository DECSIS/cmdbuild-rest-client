package eu.decsis.cmdbuild.rest

import com.mashape.unirest.http.ObjectMapper
import groovy.json.JsonException
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class UnirestObjectMapper implements ObjectMapper {

    JsonSlurper jsonSlurper = new JsonSlurper()

    public <T> T readValue(String value, Class<T> valueType) {
        try {
            return jsonSlurper.parseText(value)
        } catch (IllegalArgumentException e) {
            return null
        } catch (JsonException e) {
            return extractErrorInformation(value)
        } catch (e) {
            println value
            throw new RuntimeException(e);
        }
    }

    private LinkedHashMap<String, String> extractErrorInformation(String value) {
        def result = [error: "not in json format", detail: value]
        if (value.contains("PSQLException")) {
            result.error = value.find(/ERROR:.*/)
        }
        return result
    }

    public String writeValue(Object value) {
        try {
            return JsonOutput.toJson(value)
        } catch (e) {
            println value
            throw new RuntimeException(e);
        }
    }

}
