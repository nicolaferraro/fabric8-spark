package io.fabric8.spark.maven.plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.fabric8.maven.core.util.Configs;
import io.fabric8.maven.core.util.MavenUtil;
import io.fabric8.maven.docker.config.ImageConfiguration;
import io.fabric8.maven.generator.api.GeneratorContext;
import io.fabric8.maven.generator.javaexec.JavaExecGenerator;

import org.apache.maven.plugin.MojoExecutionException;

/**
 *
 */
public class SparkGenerator extends JavaExecGenerator {

    public SparkGenerator(GeneratorContext context) {
        super(context, "spark");
    }

    private enum Config implements Configs.Key {

        image               {{ d = "radanalyticsio/radanalytics-java-spark"; }},
        clusterName         {{ d = null; }},
        delCluster          {{ d = "true"; }},
        appExit             {{ d = "false"; }};

        public String def() { return d; } protected String d;
    }

    @Override
    public boolean isApplicable(List<ImageConfiguration> list) {
        return MavenUtil.hasClass(getProject(), "org.apache.spark.SparkContext");
    }

    @Override
    protected String getFrom() {
        return getConfig(Config.image);
    }

    @Override
    protected Map<String, String> getEnv(boolean prePackagePhase) throws MojoExecutionException {
        Map<String, String> env = super.getEnv(prePackagePhase);
        if (env == null) {
            env = new HashMap<>();
        }

        String mainClass = env.get("JAVA_MAIN_CLASS");
        if (mainClass != null) {
            env.put("APP_MAIN_CLASS", mainClass);
        }

        String clusterName = getConfig(Config.clusterName);
        if (clusterName == null) {
            clusterName = MavenUtil.createDefaultResourceName(getProject(), "cluster");
        }

        env.put("OSHINKO_CLUSTER_NAME", clusterName);
        env.put("OSHINKO_DEL_CLUSTER", getConfig(Config.delCluster));
        env.put("APP_EXIT", getConfig(Config.appExit));
        env.put("APP_FILE", getProject().getBuild().getFinalName() + ".jar");

        return env;
    }


}