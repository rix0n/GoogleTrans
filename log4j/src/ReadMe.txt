#不同的包写入不同的日志文件
log4j.logger.com.filter=info,filter  

log4j.appender.filter=org.apache.log4j.RollingFileAppender  
log4j.appender.filter.File=../logs/filter.log  
log4j.appender.filter.MaxFileSize=1024KB  
log4j.appender.filter.MaxBackupIndex=5  
log4j.appender.filter.Append=true  
log4j.appender.filter.layout=org.apache.log4j.HTMLLayout  
log4j.appender.filter.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} [%c]-[%p] %m%n

#日志文件指定大小，指定生成文件个数
log4j.logger.test=info,filter  

log4j.appender.filter=org.apache.log4j.RollingFileAppender  
log4j.appender.filter.File=../logs/filter.log  
log4j.appender.filter.MaxFileSize=1024KB  
log4j.appender.filter.MaxBackupIndex=5  
log4j.appender.filter.Append=true  
log4j.appender.filter.layout=org.apache.log4j.HTMLLayout  
log4j.appender.filter.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} [%c]-[%p] %m%n

#关闭log
log4j.logger.com.abc.def=OFF
