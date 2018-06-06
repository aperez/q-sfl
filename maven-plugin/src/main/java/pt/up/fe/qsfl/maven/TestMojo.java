package pt.up.fe.qsfl.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import pt.up.fe.qsfl.maven.service.InstrumentationRecorderService;

@Mojo(name = "test")
@Execute(lifecycle = "qsfl", phase = LifecyclePhase.TEST)
public class TestMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		InstrumentationRecorderService service = InstrumentationRecorderService.getService("");
		int retries = 10;

		while (!service.isFinished() && retries > 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			retries--;
		}

		getLog().info("Finished gathering qualitative landmarks.");
		getLog().info("");
	}

}
