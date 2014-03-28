package me.xyzlast.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.compile.JavaCompile

/**
 * Created by ykyoon on 14. 3. 28.
 * QueryDSL Q-classes generator for gradle
 */
class QueryDSLSupportPlugin implements Plugin<Project> {
    public static final String DIST = 'src/main/generated'

    @Override
    void apply(Project project) {
        project.plugins.apply("java")
        project.sourceSets {
            generated {
                java {
                    srcDirs = [ DIST ]
                }
            }
        }
        project.task(type: JavaCompile, "generateQueryDSL") << {
            source = project.sourceSets.main.java

            if(project.configurations.hasProperty("provided")) {
                classpath = project.configurations.compile + project.configurations.provided
            } else {
                classpath = project.configurations.compile
            }
            options.compilerArgs = [
                    "-proc:only",
                    "-processor", "com.mysema.query.apt.jpa.JPAAnnotationProcessor"
            ]
            destinationDir = project.file(DIST)
        }

        project.task("deleteQClasses") << {
            project.delete project.file(DIST)
        }

        Task compileJavaTask = project.getTasksByName("compileJava", true).iterator().next()
        compileJavaTask.dependsOn "generateQueryDSL"
        compileJavaTask.source project.file(DIST)

        Task cleanTask = project.getTasksByName("clean", true).iterator().next()
        cleanTask.dependsOn "deleteQClasses"
    }
}
