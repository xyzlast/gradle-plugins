package me.xyzlast.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.apache.tools.ant.taskdefs.condition.Os
import groovy.json.JsonSlurper
import org.gradle.api.Task
import org.gradle.api.tasks.Exec

/**
 * Created by ykyoon on 14. 3. 28.
 */
public class BowerInstallPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.plugins.apply("java")
        project.plugins.apply("war")

        project.task("bowerInstall") << {
            try {
                if(Os.isFamily(Os.FAMILY_WINDOWS)) {
                    project.exec {
                        commandLine = ['cmd', 'bower', 'install']
                    }
                } else {
                    project.exec {
                        commandLine = ['bower', 'install']
                    }
                }
            } catch (Exception ex) {
                println "Run bower failed!"
                println ex.getMessage()
                println ex.printStackTrace()
                return
            }

            try {
                def jsonHandler = new JsonSlurper()
                def jsonFile = project.file("bower.json")
                def conf = jsonHandler.parseText(jsonFile.getText("UTF-8"))
                def pathMap = [:]
                conf.install.path.each {
                    pathMap.put(it.key, it.value)
                }
                conf.install.sources.each {
                    it.value.each { f ->
//                        def sourceFile = project.file(f)
//                        String sourceName = sourceFile.name
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
            } catch(Exception ex) {
                println "bower.json is wrong. please check install, install.path, install.sources properties"
                ex.printStackTrace()
                return
            }
        }
        Task warTask = project.getTasksByName("war", true).iterator().next()
        warTask.dependsOn "bowerInstall"
    }
}
