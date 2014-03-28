package me.xyzlast.gradle.plugins

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert
import org.junit.Test
/**
 * Created by ykyoon on 14. 3. 28.
 */
public class LocalResourceTest {
    @Test
    public void applyLocalResource() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'local-resource'

        boolean includeHostName = false
        def hostName = InetAddress.getLocalHost().getHostName()
        project.sourceSets.main.resources.srcDirs.each {
            includeHostName = it.name.endsWith(hostName)
            if(includeHostName) {
                return true
            }
        }
        Assert.assertTrue(includeHostName)
    }

    @Test
    public void applyTargetResource() {
        Project project = ProjectBuilder.builder().build()
        project.setProperty("target", "public")
        project.apply plugin: 'local-resource'

        boolean includeHostName = false
        def hostName = InetAddress.getLocalHost().getHostName()
        project.sourceSets.main.resources.srcDirs.each {
            includeHostName = it.name.endsWith(hostName)
            if(includeHostName) {
                return true
            }
        }
        Assert.assertTrue(!includeHostName)

        boolean includeTargetName = false;
        def targetName = "-public"
        project.sourceSets.main.resources.srcDirs.each {
            includeTargetName = it.name.endsWith(targetName)
            if(includeTargetName) {
                return true
            }
        }
        Assert.assertTrue(includeTargetName)

    }

}
