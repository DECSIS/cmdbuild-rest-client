package eu.decsis.cmdbuild

import eu.decsis.cmdbuild.rest.CmdBuildRestDatasource
import eu.decsis.cmdbuild.rest.RestDatasource

abstract class CmdBuildApiConfig {

    static RestDatasource restDatasource = CmdBuildRestDatasource.instance
}
