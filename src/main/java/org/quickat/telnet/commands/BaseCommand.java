package org.quickat.telnet.commands;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.Collections;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
public abstract class BaseCommand implements Command {
    @Autowired
    private VelocityEngine velocityEngine;

    @Override
    public String getHelpMessage() {
        return evaluateTemplate("telnet/" + getClass().getSimpleName() + "/help.vm");
    }

    protected String evaluateLocalTemplate(String templateName) {
        return evaluateTemplate(getLocalTemplatePath(templateName));
    }

    private String getLocalTemplatePath(String templateName) {
        return "telnet/" + getClass().getSimpleName() + "/" + templateName;
    }

    protected String evaluateTemplate(String templateName) {
        return evaluateTemplate(templateName, Collections.<String, Object>emptyMap());
    }

    protected String evaluateLocalTemplate(String templateName, Map<String, Object> parameters) {
        return evaluateTemplate(getLocalTemplatePath(templateName), parameters);
    }

    protected String evaluateTemplate(String templateName, Map<String, Object> parameters) {
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "UTF-8", parameters);
    }
}
