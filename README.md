# simple-spark-page-rank
Simple self-contained application to run Page Rank using core Spark API
on graphx-wiki dataset from AMP Camp 5 hands-on exercises: http://ampcamp.berkeley.edu/5/exercises/.
The description of dataset is given here http://ampcamp.berkeley.edu/5/exercises/graph-analytics-with-graphx.html#load-the-wikipedia-articles.
Note that the AMP Camp exercise considers using GraphX API to run Page Rank on the dataset while here Page Rank is implemented using core Spark API.

The algorithm is taken from Matei Zaharia's slides on Writing Standalone Spark Programs:
http://ampcamp.berkeley.edu/wp-content/uploads/2012/06/matei-zaharia-part-2-amp-camp-2012-standalone-programs.pdf.

In a result the titles of ten most important articles will be printed out with their corresponding  pageranks

```bash
# Package a jar containing your application
$ sbt package

# Use spark-submit to run the application
$ YOUR_SPARK_HOME/bin/spark-submit \
--class "PageRankApp" \
--master local[*] \
target/scala-2.10/pagerank-project_2.10-1.0.jar \
10 `#number of iterations` \
/Path/to/ampcamp5/data/graphx/graphx-wiki-vertices.txt \
/Path/to/ampcamp5/data/graphx/graphx-wiki-edges.txt
...
160.9199240266459: University of California, Berkeley
87.4129126481169: Berkeley, California
18.83560051236172: Uc berkeley
14.97559158865839: Berkeley Software Distribution
13.639063509938333: George Berkeley
12.279267792312206: Lawrence Berkeley National Laboratory
10.569460619054235: Xander Berkeley
9.005556171979112: Berkeley Hills
8.988606212424607: Busby Berkeley
8.582358698896833: Berkeley County, South Carolina```