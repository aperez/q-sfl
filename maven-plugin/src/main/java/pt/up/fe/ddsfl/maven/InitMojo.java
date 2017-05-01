package pt.up.fe.ddsfl.maven;

import java.util.Map;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import pt.up.fe.ddsfl.instrumenter.agent.AgentConfigs;

@Mojo(name = "init")
public class InitMojo extends AbstractMojo {

    static final String DDSFL_ARTIFACT = "pt.up.fe.ddsfl:ddsfl-instrumenter";

    @Parameter(property = "plugin.artifactMap")
    private Map<String, Artifact> pluginArtifactMap;

    @Parameter(property = "project")
    protected MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        Artifact artifact = pluginArtifactMap.get(DDSFL_ARTIFACT);

        String agentFilename = artifact.getFile().getAbsolutePath();
        
        AgentConfigs agentConfigs = new AgentConfigs();
        agentConfigs.addPrefixToFilter("org.apache.maven.", "junit.", "org.junit.", "org.hamcrest.", "org.mockito.");

        StringBuilder sb = new StringBuilder();
        sb.append(project.getProperties().getProperty("argLine", ""));
        sb.append(" -javaagent:\"");
        sb.append(agentFilename);
        sb.append("\"=");
        sb.append(agentConfigs.serialize());
        
        System.out.println("command line: " + sb.toString());
        project.getProperties().setProperty("argLine", sb.toString());
    }

}
