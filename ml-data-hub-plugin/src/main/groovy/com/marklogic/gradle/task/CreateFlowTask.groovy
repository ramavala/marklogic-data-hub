package com.marklogic.gradle.task

import com.marklogic.gradle.exception.FlowAlreadyPresentException
import com.marklogic.gradle.exception.FlowNameRequiredException
import com.marklogic.hub.FlowManager
import com.marklogic.hub.flow.Flow
import org.gradle.api.tasks.TaskAction

class CreateFlowTask extends HubTask {

    @TaskAction
    void createFlow() {
        def propName = "flowName"
        def flowName = project.hasProperty(propName) ? project.property(propName) : null
        if (flowName == null) {
            throw new FlowNameRequiredException()
        }

        FlowManager flowManager = getFlowManager()
        if (flowManager.getFlow(flowName.toString()) == null) {
            Flow flow = flowManager.createFlow(flowName.toString())
            flowManager.saveFlow(flow)
        } else {
            throw new FlowAlreadyPresentException()
        }
    }
}