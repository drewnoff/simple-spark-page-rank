/* PageRank.scala */
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object PageRankApp {
  def main(args: Array[String]) {
    val iterations = args(0).toInt
    val articlesFile = args(1)
    val edgesFile =  args(2)
    val conf = (new SparkConf()
      .setAppName("Wiki Page Rank")
    )

    println(s"starting page rank with $iterations iterations")

    val sc = new SparkContext(conf)

    val articles = sc.textFile(articlesFile, 2).map { line =>
      val fields = line.split('\t')
      (fields(0).toLong, fields(1))
    }

    val links = sc.textFile(edgesFile, 2).map { line =>
      val fields = line.split('\t')
      (fields(0).toLong, fields(1).toLong)
    }.groupByKey()

    // init ranks
    var ranks = articles.map { case (articleId, title) =>  (articleId, 1.0) }
    for (i <- 1 to iterations) {
      val contribs = links.join(ranks).flatMap {
        case (artibleId, (links, rank)) =>
          links.map(dest => (dest, rank/links.size))
      }
      ranks = contribs.reduceByKey(_ + _)
        .mapValues(0.15 + 0.85 * _)
    }

    articles.join(ranks).top(10) {
      Ordering.by((entry: (Long, (String, Double))) => entry._2._2)
    }.foreach(t => println(t._2._2 + ": " + t._2._1))
  }
}
