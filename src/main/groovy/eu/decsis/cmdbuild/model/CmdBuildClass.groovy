package eu.decsis.cmdbuild.model

class CmdBuildClass implements CmdBuildRepository<CmdBuildClass>{

    String _id
    String description
    String description_attribute_name
    String name
    String parent
    boolean prototype
    String resourceName() { return "classes" }

    List<Card> getCards(){
        return this.<Card>listChildren("cards")
    }

    List<Attribute> getAttributtes(){
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

    Card findCardBy(Map<String, String> filters) {
        this.<Card>findChild('cards',filters)
    }

    List<Card> findAllCardsBy(Map<String, String> filters, Integer limit = null) {
        this.<Card>findAllChildren('cards',filters, limit)
    }
}
