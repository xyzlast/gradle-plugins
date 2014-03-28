package me.xyzlast.gradle.plugins

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert
import org.junit.Test
/**
 * Created by ykyoon on 14. 3. 28.
 */
public class BowerInstallPluginTest {

    @Test
    public void applyBowerInstaller() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'bower-installer'
        Assert.assertTrue(project.plugins.findPlugin("bower-installer") instanceof BowerInstallPlugin)
    }
}
