package io.fabric8.spark.maven.plugin;

import io.fabric8.maven.enricher.api.BaseEnricher;
import io.fabric8.maven.enricher.api.EnricherContext;

/**
 *
 */
public class SparkEnricher extends BaseEnricher {

    public SparkEnricher(EnricherContext buildContext) {
        super(buildContext, "spark");
    }


}
