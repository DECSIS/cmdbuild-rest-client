package eu.decsis.cmdbuild.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class CmdBuildClass implements CmdBuildRepository<CmdBuildClass>{

    String _id
    String description
    String description_attribute_name
    String name
    String parent
    boolean prototype
    def defaultOrder

    static String resourceName() { return "classes" }

    List<Card> getCards(){
        return this.<Card>listChildren("cards")
    }

    List<Attribute> getAttributes(){
        return this.<Attribute>listChildren("attributes")
    }

    void addCard(card) {
        try {
            addChildren('cards',card)
        } catch (e) {
            printMandatoryAttributes()
            throw e
        }
    }

    private void printMandatoryAttributes(){
        def mandatoryAttributes =  attributes.findAll { it.mandatory }.collect { "${it._id}(${it.type})"}.join(', ')
        println "The following attributes are mandatory: ${mandatoryAttributes}"
    }

    Card findCardBy(Map<String, String> filters, Map<String, String> order = null) {
        this.<Card>findChild('cards',filters, order)
    }

    List<Card> findAllCardsBy(Map<String, String> filters, Integer limit, Map<String, String> sortMap = null) {
        this.<Card>findAllChildren('cards',filters, limit, sortMap)
    }

}
