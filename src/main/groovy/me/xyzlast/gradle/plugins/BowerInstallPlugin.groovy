package me.xyzlast.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.apache.tools.ant.taskdefs.condition.Os
import groovy.json.JsonSlurper
import org.gradle.api.Task
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Exec

/**
 * Created by ykyoon on 14. 3. 28.
 */
public class BowerInstallPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.plugins.apply("java")
        project.plugins.apply("war")

        project.task(dependsOn: 'bowerInstall', "copyJS") {
            Task warTask = project.getTasksByName("war", true).iterator().next()
            warTask.dependsOn "copyJS"
            doLast {
                def jsonHandler = new JsonSlurper()
                def jsonFile = project.file("bower.json")
                def conf = jsonHandler.parseText(jsonFile.getText("UTF-8"))
                def pathMap = [:]
                conf.install.path.each {
                    pathMap.put(it.key, it.value)
                }
                conf.install.sources.each {
                    it.value.each { f ->
                        int dotPos = f.lastIndexOf(".")
                        String ext = f.substring(dotPos + 1, f.length())
                        if (pathMap.containsKey(ext)) {
                            project.copy {
                                from f
                                into pathMap.get(ext)
                            }
                        }
                    }
                }
            }
        }

        project.task(type: Exec, "bowerInstall") {
            try {
                if(Os.isFamily(Os.FAMILY_WINDOWS)) {
                    commandLine = ['cmd', 'bower', 'install']
                } else {
                    commandLine = ['bower', 'install']
                }
            } catch (Exception ex) {
                println "Run bower failed!"
                println ex.getMessage()
                println ex.printStackTrace()
            }
        }
    }
}
