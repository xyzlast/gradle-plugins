package me.xyzlast.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by ykyoon on 14. 3. 28.
 * Local Resources support in gradle.
 */
class LocalResource implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.plugins.apply("java")
        if(project.hasProperty("target")) {
            project.sourceSets {
                main.resources.srcDirs = ['src/main/resources', "src/main/resources-${project.target}"]
                test.resources.srcDirs = ['src/test/resources', "src/test/resources-" + "${project.target}" ]
            }
        } else {
            String hostname = getHostName()
            project.sourceSets {
                main.resources.srcDirs = ['src/main/resources', "src/main/resources-" + hostname]
                test.resources.srcDirs = ['src/test/resources', "src/test/resources-" + hostname]
            }
        }
    }

    public static String getHostName() {
        String hostname = InetAddress.getLocalHost().getHostName();
        if(hostname.endsWith('.local')) {
            //NOTE : in mac, hostname must contain '.local'. so remove it.
            hostname = hostname.replace(".local", '')
        }
        hostname = hostname.replace(".", "")
        hostname = hostname.toLowerCase(Locale.ENGLISH)
        return hostname
    }
}
