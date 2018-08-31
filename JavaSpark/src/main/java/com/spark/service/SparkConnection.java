package com.spark.service;




import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapRowTo;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;



import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.spark.connector.cql.CassandraConnector;
import com.datastax.spark.connector.japi.CassandraJavaUtil;
import com.spark.model.Offices_all;
import com.spark.model.TrackNew;

import scala.Tuple2;

/**
 * 
 * @author praveenkumar
 *
 */
@Service
public class SparkConnection {

	@Value("${cassandra.host}")
	String cassandraHost;
	
/*public static void main(String[] args) {

    SparkConf conf = new SparkConf();
    conf.setAppName("Java API demo");
    conf.setMaster("local[*]");
    conf.set("spark.cassandra.connection.host", "localhost");
    
 
  SparkSession sparkSession =SparkSession.builder().appName("Java sparks Sql Example").config(conf).getOrCreate();
  JavaSparkContext sc = new JavaSparkContext(sparkSession.sparkContext());
//  convertRDDtoDataSet(sparkSession,sc);
  convertRDDtoDataSet1(sparkSession,sc);
  
//    JavaSparkContext sc = new JavaSparkContext(conf);
    
    
    //JavaSQLContext jsContext=new JavaSQLContext(sc);
   //deprecated 
   // SQLContext sqlContext = new SQLContext(sc);
//    SQLContext sqlContext = new SQLContext(sparkSession);
//    sparkSession.close();
    
   // generateData(sc);
  
   // compute(sc);
  
   // showResults(sc);
  
   // queryData(sc);
  
//    accessTableWitRDD(sc);
  
   // querySQLData(sqlContext);
  
    sc.stop();
    sparkSession.stop();
    
    
    //For 2.1.0
     * 
     * 	SparkContext sparkContext = null;
	//SparkSession sparkSession = null;
	JavaSparkContext  sc = null;
try{
  SparkConf conf = new SparkConf();
              conf.setAppName("test");
            //  conf.setMaster(masterUri);
             conf.setMaster("spark://127.0.0.1:7077");//set the master Ip
                // conf.setMaster("spark://test.yarn.spark.net:7077");
            // conf.set("spark.driver.host","127.0.0.1");
          //   conf.set(set, setUri);
              conf .set("spark.cassandra.auth.username","MIIEowIBAAKCAQEAQ9ZV9Jnl0Zq5WNAJzr2fP43S+vq7jlCWMyVy9xhbdtw2uGuh+QMWE3TnwCyMywpeKFLfNNUSTAanDZGwPfQwiE8LB1v3SLbreUccoWexlr/9vmPY383+y7RQhvEUEFypJ7AGVM9A6UPX7uiZvk5eLQkN5g+hjbE8MUT39Wpe0wMskK9dJCStbCiDTSgNx6Bw3t2OZhtgklC7JMS8kdI5393iAFRcexmvsHhKSmgprvIoII+HDvHTfVF9g3b2rAGHVoI4CakfLVktcHKjGBf8BzcBGN5u/dfKUYXy2VZelHgo14xu2s7S0CotReoCcOcwIDAQABAoIBAGUFV5RYO3tG1IB2Czw7xLERT9trJEjsDEFzRYjCcy+dnFJ41wnuh9pB6OrtmVc1A8Yb9EMY4OBPe1hDFCUfhVAb2l+LJx3LVF1HbqVWFcViUFhYB/0wLlzzNvcrnIHjkY19+dqP6IRmLm9f7/N9xrXvSFaaGwAOQFt63fMkdrEwcaufBuUyNl6jz+h42lNoNvr4eK/cCueF4mmk0pwLdK4lkUUzKZsxhC9UHonVdXOqGVWtwB6A3MLJ5CWr7C/s5gypZj4d8ZQeAtZkzN46z/pjiI2d9eI21sDdc3phcRx+KCQkWY/67QYa9Iq8+9GqUjJR3KkIm6aDXkm06ZjT5aECgYEA3byWN1Ed1wvZWquYEwriuXXPPobZ3xUCcXJFSKoqWdGF2Psym9KUG2f5CDCFheGrCOfU1yhTtCdjNw6qP+T3wqBA6vKE5IBFfwfOPE9SE2YbKvJygptz2iLNWQOftfJB6YKIBuRVnsbX6mwuHZy8HnXm+yoQzJc1nBKmEM1Q2XECgYEAza2/0hA/EikPPlGu2t63pZC/fv1oOaJDikHXgmSdHFVlejLSQ15HkgqN4Dc0P38RS04S/AwSxtWeqzsrfyWjTEuzXy23Tf1h8DEQt1d0hLwVIFrixJbbZIESZFc84hl2GlEynALaoeYDxhspXEEwKir3cDxhLi7G9ilvusSklCMCgYEAwxpJzniIWgyp/jbYA4Fjhy4a4XiEQd7ZnHqgbdSUmR1buVUl7ae2+/pbTBJtmiS7eoWKaJqqM+0oRmyX7sqIGm8pT9F+jxQykhiLJdPhcwI3y20yxqsDoB9ZMgaXQ9/akR1ylSXaFG//0dvII/QiLmctizgzOeGeoSpjhosxpXECgYBjOO7Q6AWFPUmdRsqTy93MgYfgO1+Mbjsm95x/ywoAITJTQTEuTs04Jt9Ky1bpSuOM8J03+psUUPm6oVDahc3BgMOHpwZHGXWl6gWmbQpI/nMyqKW9MQml8p5syakVajMPfxxrJvVSJSSs7W2Gk+woa5HzEy3OmcJh/ptn/nVnAQKBgD+/Mn6mq3bX7ur061cK40hZEuF5dPs2k2FJpTU2BAkAxzChzlvMF71bYRqJGfCecWNeNg1CykyfbwXAxN/xNks/aVro2pJaK8kcOMvx5C4gzMGNKXDgU73IhPbk6QN4i9kzPjugMzrkEj22rSDgdXGbEUoSiD8wLquHhrjhcAwI");
              conf .set("spark.cassandra.auth.password", "root");
              conf.set("spark.sql.broadcastTimeout", "1200");
              conf.set("spark.driver.allowMultipleContexts","true");
              sparkContext =SparkContext.getOrCreate(conf);//.config(conf).getOrCreate();
              sc = new JavaSparkContext(sparkContext);
              System.out.println("Spark Connection");

}*/



public String  cassandraSparkCall() {

	
	System.out.println("CassandraSparkCall "+cassandraHost);
    SparkConf conf = new SparkConf();
    conf.setAppName("Java API demo");
    conf.setMaster("local[*]");
    conf.set("spark.cassandra.connection.host", cassandraHost);
    
 
    SparkSession sparkSession =SparkSession.builder().appName("Java sparks Sql Example").config(conf).getOrCreate();
  JavaSparkContext sc = new JavaSparkContext(sparkSession.sparkContext());
  convertRDDtoDataSet(sparkSession,sc);
//  convertRDDtoDataSet1(sparkSession,sc);
  
//    JavaSparkContext sc = new JavaSparkContext(conf);
    
    
    //JavaSQLContext jsContext=new JavaSQLContext(sc);
   //deprecated 
   // SQLContext sqlContext = new SQLContext(sc);
//    SQLContext sqlContext = new SQLContext(sparkSession);
//    sparkSession.close();
    
   // generateData(sc);
  
   // compute(sc);
  
   // showResults(sc);
  
   // queryData(sc);
  
//    accessTableWitRDD(sc);
  
   // querySQLData(sqlContext);
  
    sc.stop();
    sparkSession.stop();
    return "Success";

}

/*private static void moreEfficientJoin() {
    SparkConf conf = new SparkConf().setAppName("Simple Application")
                                    .setMaster("local[*]")
                                    .set("spark.cassandra.connection.host", "localhost")
                                    .set("spark.driver.allowMultipleContexts", "true");
    JavaSparkContext sc = new JavaSparkContext(conf);

    JavaRDD<DataKey> nameIndexRDD = sc.parallelize(javaFunctions(sc).cassandraTable("my_keyspace", "name", mapRowTo(DataKey.class))
                                                                    .where("name = 'John'")
                                                                    .collect());

    JavaRDD<Data> dataRDD = javaFunctions(nameIndexRDD).joinWithCassandraTable("my_keyspace", "data", allColumns, someColumns("timestamp", "id"), mapRowTo(Data.class), mapToRow(DataKey.class))
                                                       .map(new Function<Tuple2<DataKey, Data>, Data>() {
                                                           @Override
                                                           public Data call(Tuple2<DataKey, Data> v1) throws Exception {
                                                               return v1._2();
                                                           }
                                                       });
    List<Data> data = dataRDD.collect();
}*/



//DATA FRAME
private static void querySQLData(SQLContext sqlContext) {

    Dataset<Row> result = sqlContext.sql("SELECT * from head_tracknew");

    System.out.println("Show the DataFrame result:\n");

    result.show();

    System.out.println("Select the id column and show its contents:\n");

    result.select("id").show();

}

private static  void accessTableWitRDD(JavaSparkContext sc){

   /* JavaRDD<String> cassandraRDD = javaFunctions(sc).cassandraTable("test", "head_tracknew")
            .map(new Function<CassandraRow, String>() {

                @Override

                public String call(CassandraRow cassandraRow) throws Exception {

                    return cassandraRow.toString();

                }

            });

    System.out.println("\nReading Data from todolisttable in Cassandra with a RDD: \n" + StringUtils.join(cassandraRDD.toLocalIterator(), "\n"));
*/
	
	
    // javaFunctions(cassandraRDD).writerBuilder("todolist", "todolisttable", mapToRow(String.class)).saveToCassandra();
    
	long start=System.currentTimeMillis();
    JavaRDD<TrackNew> headTrackNewRdd = CassandraJavaUtil.javaFunctions(sc)
            .cassandraTable("test", "head_tracknew", mapRowTo(TrackNew.class));
    long end=System.currentTimeMillis();
    System.out.println("Total time "+(end-start));
    
    
    
    
    
    System.out.println("\nReading Data from head_tracknew in Cassandra with a RDD: \n" + StringUtils.join(headTrackNewRdd.toLocalIterator(), "\n"));
    Iterator<TrackNew> i=headTrackNewRdd.toLocalIterator();
    
    while(i.hasNext()){
    	System.out.println("Value");
    	TrackNew headTrack=i.next();
    	

    	
    	
    }

}




//RDD to DataSet
private static void convertRDDtoDataSet( SparkSession sparkSession,JavaSparkContext sc){
	
	try{
		
		long startTime = System.currentTimeMillis();
		/******/
/*		
		JavaRDD<headTrackNew> headTrackNewRdd = CassandraJavaUtil.javaFunctions(sc)
	            .cassandraTable("test", "head_tracknew", mapRowTo(headTrackNew.class));
		
		
		JavaRDD<ContactHistoryChannelNew> channelNew = CassandraJavaUtil.javaFunctions(sc)
	            .cassandraTable("test", "contacthistory_channelnew", mapRowTo(ContactHistoryChannelNew.class));
		
		
		Dataset<headTrackNew> ds = sparkSession.createDataset(
				headTrackNewRdd.rdd(), // convert a JavaRDD to an RDD
				  Encoders.bean(headTrackNew.class)
				);
		
		ds.show();
		ds.createOrReplaceTempView("track");
	
		
//		Dataset<ContactHistoryChannelNew> ds2 = sparkSession.createDataset(
//				channelNew.rdd(), // convert a JavaRDD to an RDD
//				  Encoders.bean(ContactHistoryChannelNew.class)
//				);
		
//		ds2.createOrReplaceTempView("channelNew");
		//sparkSession.sql("update track set metric = 'opened' where value > 1");

//		Dataset<Row> res=sparkSession.sql("INSERT OVERWRITE TABLE channelNew SELECT head_ref_code, customer_id, sub_id, channel FROM track") ;
		
		Dataset<Row> filteredDS = sparkSession.sql("select * from track ");

        
        String[] columns = {"sub_position","call_tracking"};*/
        
        
		/******/
		
        
//        JavaRDD<ContactHistoryChannelNew> test= CassandraJavaUtil.javaFunctions(sc)
//                .cassandraTable("keySpace", "myTable",mapRowTo(ContactHistoryChannelNew.class)).select("sub_position","call_tracking") .where("sub_position", "null");
        
        
        
        
        Dataset<Row> dataset = sparkSession.read()
                .format("org.apache.spark.sql.cassandra")
                .options(new HashMap<String, String>() {
                    {
                        put("keyspace", "test");
                        put("table", "customers");
                    }
                })
                .load();
                //.filter("sub_position is null");
        System.out.println("**********************************************************");
        dataset.show();
        
/* 1       Map<String,String> opt=new HashMap<>();
		opt.put("table", "head_tracknew");
		opt.put("keyspace", "test");
		
		String mode="append";*/
		
	//	filteredDS.write().format("org.apache.spark.sql.cassandra").options(opt)
 /*  1     
        dataset.withColumn("sub_position", lit("5")).write().format("org.apache.spark.sql.cassandra").options(opt).mode(SaveMode.Append).save();
        long endTime = System.currentTimeMillis();
 */       
     //   import org.apache.spark.sql.functions;
        //ADD auto increment Id 
/*  1      Dataset<Row> dataFrame1 = dataset.withColumn("index",functions.monotonically_increasing_id());
        
        System.out.println("Success ////////////////////////////////"+(endTime-startTime));*/
/*        Dataset<Row> dataset = sparkSession.read()
                .format("org.apache.spark.sql.cassandra")
                .options(new HashMap<String, String>() {
                    {
                        put("keyspace", "test");
                        put("table", "contacthistory_channelnew");
                    }
                })
                .load()
                .filter("value_id BETWEEN 1 AND 5");*/
        
        
        
        //test.filter(f)
        
//        Dataset<headTrackNew> testds = sparkSession.createDataset(
//				headTrackNewRdd.rdd(), // convert a JavaRDD to an RDD
//				  Encoders.bean(headTrackNew.class)
//				);
        
        
        		 // convert a JavaRDD to an RDD
				  
				
        
     /*  CassandraTableScanJavaRDD<CassandraRow> test= CassandraJavaUtil.javaFunctions(sc)
        .cassandraTable("keySpace", "myTable").select("sub_position","call_tracking") ;
       
       test.filter(test.first().)
        
        
        val empty = interactions.filter(r => r._6 == null).cache() 
        		empty.count()
        
		
		#Use withColumn to use student_id when new_student_id is not populated
		cleaned = students.withColumn("new_student_id", 
		          when(col("new_student_id").isNull(), col("student_id")).
		          otherwise(col("new_student_id")))
*/
		/*
		cleaned = students.withColumn("new_student_id", 
		          when(col("new_student_id").isNull(), col("student_id")).
		          otherwise(col("new_student_id")))
		cleaned.show()
		*/
		//ADD EXTRA COlumn
		//filteredDS.withColumn("last_date_business", coalesce(col("close_date"), current_date()));
		
		//filteredDS2.show();
		//filteredDS.join(filteredDS2);
		//sparkSession.sql("update track set metric = 'opened' where value > 1");
		
		System.out.println("------------************************-------------");
		//filteredDS
//		filteredDS.show();
//		res.show();
		//Dataset<Row> filter1= filteredDS.se
		
		
		//Write
/*		Map<String,String> opt=new HashMap<>();
		opt.put("table", "head_tracknew");
		opt.put("keyspace", "test");
		
		String mode="append";
		
		filteredDS.write().format("org.apache.spark.sql.cassandra").options(opt).mode(mode).save();
		System.out.println("inserted ");
*/		//JavaRDD<Row> test=filteredDS.javaRDD();
		
		
		
		
		//javaFunctions(headTrackNewRdd).writerBuilder("my_keyspace", "my_table", mapToRow(headTrackNew.class)).saveToCassandra();
		//test.sa
		/**
		 * 
		 * ratings.write.format("org.apache.spark.sql.cassandra").\
        options(keyspace="lens", table="ratings_by_movie").\
        save(mode="append")
		 */
	  //  javaFunctions(summariesRDD).writerBuilder("java_api", "summaries", mapToRow(Summary.class)).saveToCassandra();

		//filteredDS.filter(condition);
		
		
	}catch(Exception e){
		e.printStackTrace();
	}
	
}



/******/


//RDD to DataSet
	private static void convertRDDtoDataSet1( SparkSession sparkSession,JavaSparkContext sc){

	    try{

	        JavaRDD<Offices_all> Rdd = javaFunctions(sc)
	                .cassandraTable("test_calculator", "offices_all", mapRowTo(Offices_all.class))
	                .select(CassandraJavaUtil.column("id"),CassandraJavaUtil.column("cert"), 
	                		CassandraJavaUtil.column("name")
	                		/*,CassandraJavaUtil.column("county"),
	                		CassandraJavaUtil.column("stname"),CassandraJavaUtil.column("address"),
	                		CassandraJavaUtil.column("admin_area_level"),
	                		CassandraJavaUtil.column("lat"),
	                		CassandraJavaUtil.column("long").as("lon")
	                		*/);
	                
	        Dataset<Offices_all> ds = sparkSession.createDataset(
	        		Rdd.rdd(),   // convert a JavaRDD to an RDD
	                  Encoders.bean(Offices_all.class)
	                );

	        ds.show();
	        ds.createOrReplaceTempView("offices_all");
	   //     df.where(col("pType").isin(list: _*)
	        
	        /*List<Integer> l=new ArrayList<>();
	        l.add(1);
	        l.add(2);
	        
	        ds.select("columns").where(col("column").isin(l));*/

	        Dataset<Row> total = sparkSession.sql("select id,cert,name from offices_all ");
	        System.out.println("------------************************-------------");
	        total.show();
	        
	        
			Map<String,String> opt=new HashMap<>();
			opt.put("table", "test");
			opt.put("keyspace", "test_calc");
			
			String mode="append";
			
			total.write().format("org.apache.spark.sql.cassandra").options(opt).mode(mode).save();
	        System.out.println("Inserted ");
	       // total.toJavaRDD();
	        
	      /*  List<Test> people = Arrays.asList(
	                new Test(1, "John", new Date()),
	                
	        );*/
	        
	        
	 //       JavaRDD<Person> rdd = sc.parallelize(people);
	  //      javaFunctions(rdd).writerBuilder("ks", "people", mapToRow(Person.class)).saveToCassandra();
	        
	        
	    /*  Map<String,String> opt=new HashMap<String, String>();
	        opt.put("table", "test");
	        opt.put("keyspace", "test_calculator");
	        String mode="append";
	        try{
	        total.write().format("org.apache.spark.sql.cassandra").options(opt).mode(mode).save();
	        System.out.println("inserted ");
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        
	        try{
	    CassandraJavaUtil.javaFunctions(Rdd).writerBuilder("test_cal", "test", 
	    		 CassandraJavaUtil.mapToRow(Offices_all.class)).saveToCassandra();
	        }catch(Exception e)
	        {
	        	System.out.println(e.getMessage());
	        }*/
	        //filteredDS.filter(condition);


	    }catch(Exception e){
	    	System.out.println(e.getMessage());	    
	    	}
	}
/******/



private static void queryData(JavaSparkContext sc) {
	 String query = "SELECT * FROM test.head_tracknew";

    CassandraConnector connector = CassandraConnector.apply(sc.getConf());

    try (Session session = connector.openSession()) {

        ResultSet results = session.execute(query);

        System.out.println("\nQuery all results from cassandra's todolisttable:\n" + results.all());

     //   ResultSet results1 = session.execute(query1);

   //     System.out.println("\nSaving RDD into a temp table in casssandra then query all results from cassandra:\n" + results1.all());

    }

}





}


