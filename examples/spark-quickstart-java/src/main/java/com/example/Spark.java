package com.example;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.LoggerFactory;


public class Spark {

    public static void main(String[] args) throws Exception {
        SparkConf sparkConf = new SparkConf().setAppName("JavaSparkPi");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);

        int slices = (args.length == 1) ? Integer.parseInt(args[0]) : 2;
        int n = 100000 * slices;
        List<Integer> l = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            l.add(i);
        }

        JavaRDD<Integer> dataSet = jsc.parallelize(l, slices);

        int count = dataSet.map(e -> {
                double x = Math.random() * 2 - 1;
                double y = Math.random() * 2 - 1;
                LoggerFactory.getLogger("xxx").info("Hello world {}, {}", x, y);
                return (x * x + y * y < 1) ? 1 : 0;
        }).reduce((x, y) -> x + y);

        System.out.println("Fabric8io!!! Pi is rouuuughly " + 4.0 * count / n);

        jsc.stop();
    }

}
