package eu.decsis.cmdbuild.model

class Card implements CmdBuildRepository<Card>{
    String _id
    String _type
    def dynamicProperties= [:]

    def propertyMissing(String name, value) { dynamicProperties[name] = value }
    def propertyMissing(String name) { dynamicProperties= [name] }


}
