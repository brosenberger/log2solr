# slf4j-solrappender

The slf4j-solrappender should be a small addition to be able to send log events from an application to an external search server.

To run the test, please download a solr 4.9 instance (http://lucene.apache.org/solr/downloads.html) and run it locally. The needed schema file can be found in the test-resources of this project

2015-03-12
 * [R1] add interface for solr document enricher
 * initial version of a logback appender
 ->  logs some basic information (message, formatted message, level and time)

# Upcomming Requirements

*R1:	create possibility to enable custom SolrInputDocument filler from event (or additional data)
*R2:	create seperate modules for core, logback dependent things (to enable different appender implementations with base code)
*R3:	create appender for log4j
*R4:	add fallback logger if anything happens with solr (currently on sysout is done which is not preferrable)
*R5:	add solr cloud support
 
# Issues

*I1 blocking log writing if an exception happens on the solr server (e.g. through wrong type of data in the stored field)
