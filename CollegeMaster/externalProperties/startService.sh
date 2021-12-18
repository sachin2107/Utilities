#here we are defining 2 properties file, one is from classpath and second one is external
#Priority is last defined file i.e. external one
#If we include external file as last configuration then it will consider properties from last files
java -jar -Dspring.config.location=classpath:application.properties,D:\Ankita_Programs\Sachin_Programs_Citi\CollegeMaster\externalProperties\application.properties D:\Ankita_Programs\Sachin_Programs_Citi\CollegeMaster\target\CollegeMaster-0.0.1-SNAPSHOT.jar