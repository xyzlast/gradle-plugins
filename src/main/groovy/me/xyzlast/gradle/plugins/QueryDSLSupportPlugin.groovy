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
    void apply(Project target) {
        target.plugins.apply("java")
        target.sourceSets {
            generated {
                java {
                    srcDirs = [ DIST ]
                }
            }
        }

        target.configurations {
            querydsl
        }

        target.extensions.create("querydsl", QueryDSLPluginExtension)

        target.dependencies {
            compile("com.mysema.querydsl:querydsl-core:${target.querydsl.version}")
            compile("com.mysema.querydsl:querydsl-jpa:${target.querydsl.version}")
            compile("com.mysema.querydsl:querydsl-sql:${target.querydsl.version}")
            querydsl("com.mysema.querydsl:querydsl-apt:${target.querydsl.version}") {
                exclude group: 'com.google.guava'
            }
        }

        target.task(type: JavaCompile, "generateQueryDSL") {
            source = target.sourceSets.main.java
            classpath = target.configurations.compile + target.configurations.querydsl
            options.compilerArgs = [
                    "-proc:only",
                    "-processor",
                    "com.mysema.query.apt.jpa.JPAAnnotationProcessor",
            ]
            options.encoding = 'UTF-8'
            destinationDir = target.sourceSets.generated.java.srcDirs.iterator().next()
        }

        target.task("deleteQClasses") {
            doLast {
                target.delete target.file(DIST)
            }
        }

        Task compileJavaTask = target.getTasksByName("compileJava", true).iterator().next()
        compileJavaTask.dependsOn "generateQueryDSL"
        compileJavaTask.source target.file(DIST)

        Task cleanTask = target.getTasksByName("clean", true).iterator().next()
        cleanTask.dependsOn "deleteQClasses"
    }
}


class QueryDSLPluginExtension {
    String version = "3.2.4"
}