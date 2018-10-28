package org.example.greeting;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;

import static org.junit.Assert.*;

public class GreetingPluginTest {

    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();

    private File buildFile;

    @Before
    public void setup() throws IOException {
        buildFile = testProjectDir.newFile("build.gradle");
    }

    @Test
    public void helloTaskShouldGreetAppropriately() throws IOException {
        String buildFileContent = "" +
                "plugins {" +
                "   id 'org.samples.greeting'" +
                "}";
        writeFile(buildFile, buildFileContent);

        BuildResult result = GradleRunner.create()
                .withProjectDir(testProjectDir.getRoot())
                .withArguments("hello")
                .withPluginClasspath()
                .build();

        assertTrue(result.getOutput().contains("Hello, World!"));
        assertEquals(SUCCESS, result.task(":hello").getOutcome());
    }

    private void writeFile(File destination, String content) throws IOException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(destination))) {
            output.write(content);
        }
    }
}
