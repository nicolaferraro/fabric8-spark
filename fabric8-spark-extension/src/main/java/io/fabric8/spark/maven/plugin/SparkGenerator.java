package io.fabric8.spark.maven.plugin;

import java.util.List;

import io.fabric8.maven.core.util.MavenUtil;
import io.fabric8.maven.docker.config.ImageConfiguration;
import io.fabric8.maven.generator.api.MavenGeneratorContext;
import io.fabric8.maven.generator.api.support.BaseGenerator;

import org.apache.maven.plugin.MojoExecutionException;

/**
 *
 */
public class SparkGenerator extends BaseGenerator {

    public SparkGenerator(MavenGeneratorContext context) {
        super(context, "spark");
    }

    @Override
    public boolean isApplicable(List<ImageConfiguration> list) {
        return MavenUtil.hasClass(getProject(), "org.apache.spark.SparkContext");

    }

    @Override
    public List<ImageConfiguration> customize(List<ImageConfiguration> list) throws MojoExecutionException {
        return null;
    }
}
