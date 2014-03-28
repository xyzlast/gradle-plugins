package me.xyzlast.gradle.plugins

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert
import org.junit.Test

/**
 * Created by ykyoon on 14. 3. 28.
 */
public class QueryDSLSupportPluginTest {

    @Test
    public void queryDSLSupportTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'querydsl'
        Assert.assertTrue(project.plugins.findPlugin("querydsl") instanceof QueryDSLSupportPlugin)
    }
}
