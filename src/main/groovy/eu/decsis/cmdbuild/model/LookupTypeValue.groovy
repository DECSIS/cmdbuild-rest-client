package eu.decsis.cmdbuild.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode()
class LookupTypeValue {
    String _id
    String _type
    Boolean active
    String code
    private Boolean default_
    String description
    Integer number
    String parent_id
    String parent_type

    void setDefault(Boolean default_){
        this.default_ = default_
    }

    Boolean isDefault() {
        default_
    }

}
