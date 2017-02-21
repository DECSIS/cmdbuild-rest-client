package eu.decsis.cmdbuild.rest

@Singleton
class FakeRestDatasource extends RestDatasource{

    private Map<Integer,String> getResponses = [
             1257674046: '{"data":{"_id":"Building","description":"Building","description_attribute_name":"Description","name":"Building","parent":"Site","prototype":false}}',
             1799805129: '{"data":{"_id":"Consumption","description":"Consumption","description_attribute_name":"Description","name":"Consumption","parent":null,"prototype":false}}',
             1007846965: '{"data":[{"Address":"Parque da Ci\u00eancia e Tecn do Alentejo, R. Circular Norte, Parque Ind e Tecnol\u00f3gico de \u00c9vora, L2 Horta das Figueiras, ","Area":null,"Availability":72129,"Category":null,"City":"\u00c9vora","CleanableArea":300.0,"ClimaticZone":240557,"Code":"0123","Complex":null,"Condition":31,"Country":240555,"CoveredArea":340.0,"Criticality":35,"Description":"0123 Data Center DECSIS","Floors":1,"GlazedArea":null,"Group":null,"IsInBuilding":null,"IsInComplex":null,"IsInFloor":null,"IsInRoom":null,"IsInUnit":null,"LastCheckDate":"2016-03-14T00:00:00","MainUse":44623,"Name":"Data Center DECSIS","Notes":null,"Rooms":null,"TotalGrossArea":600.0,"TotalHeatedVolume":900.0,"TotalNetArea":500.0,"TotalVolume":1000.0,"Units":null,"ZIP":"7005-841","_id":240554,"_type":"Building"}],"meta":{"total":1}}'
    ]

    @Override
    def doGet(String path, Object queryParameters) {
        return null
    }

    @Override
    def doPost(String path, Object payload, Object queryParameters) {
        return null
    }

    @Override
    def doPut(String path, Object payload, Object queryParameters) {
        return null
    }

    @Override
    def doDelete(String path, Object queryParameters) {
        return null
    }
}
