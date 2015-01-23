package demo;

import java.io.IOException;

//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.TextField;
//import org.apache.lucene.index.DirectoryReader;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
//import org.apache.lucene.queryparser.classic.ParseException;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.RAMDirectory;
//import org.apache.lucene.util.Version;
//import org.wltea.analyzer.lucene.IKAnalyzer;

public class demo {/*

	public static void main(String[] args) throws IOException, ParseException {
		
//		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
		Analyzer analyzer = new IKAnalyzer(); 

		// Store theindex in memory:
		// 索引存到内存中的目录
		Directory directory = new RAMDirectory();
		// To store anindex on disk, use this instead:
		// Directory directory = FSDirectory.open("/tmp/testindex");
		// 配置索引
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47,
				analyzer);
		IndexWriter iwriter = new IndexWriter(directory, config);
		// 这里，将5篇文档filedname信息和content信息存入索引
		Document doc[] = new Document[5];
		for (int i = 0; i < 5; i++)
			doc[i] = new Document();
		String[] text = { "今天是个好天气This is the text to be greatly indexed.",
				"This is great", 
				"I love apple and pear.",
				"Apple computer great",
				"It's a very great day in this condition",
				"春季来了This is great content, cheers." };

		doc[0].add(new Field("fieldname", text[0], TextField.TYPE_STORED));
		doc[0].add(new Field("content", text[5], TextField.TYPE_STORED));
		doc[1].add(new Field("fieldname", text[1], TextField.TYPE_STORED));
		doc[2].add(new Field("fieldname", text[2], TextField.TYPE_STORED));
		doc[3].add(new Field("fieldname", text[3], TextField.TYPE_STORED));
		doc[4].add(new Field("fieldname", text[4], TextField.TYPE_STORED));
		iwriter.addDocument(doc[0]);
		iwriter.addDocument(doc[1]);
		iwriter.addDocument(doc[2]);
		iwriter.addDocument(doc[3]);
		iwriter.addDocument(doc[4]);
		iwriter.close();

		// Now searchthe index:
		// 索引构建完毕，准备搜索。
		// 设定搜索目录
		DirectoryReader ireader = DirectoryReader.open(directory);
		IndexSearcher isearcher = new IndexSearcher(ireader);
		// Parse asimple query that searches for "text":
		// QueryParserparser = new QueryParser(Version.LUCENE_CURRENT,
		// "fieldname", analyzer);
		// 使用同样的方式对多field进行搜索
		String[] multiFields = { "fieldname", "content" };
		MultiFieldQueryParser parser = new MultiFieldQueryParser(
				Version.LUCENE_47, multiFields, analyzer);
		
		 
        String processedString; 
        for (int i = 0; i < args.length;i++) 
            processedString+= args[i] + " "; 
        processedString =processedString.replace("\"", ""); 
        System.out.println(processedString);
          

		// 设定具体的搜索词
//		Query query = parser.parse(" great this");
		Query query = parser.parse("春");
		ScoreDoc[] hits = isearcher.search(query, null, 10).scoreDocs;
		// assertEquals(1, hits.length);
		System.out.println("Searched " + hits.length + " documents.");
		// Iteratethrough the results:
		for (int i = 0; i < hits.length; i++) {
			Document hitDoc = isearcher.doc(hits[i].doc);
			String[] scoreExplain = null;
			// scoreExplain可以显示文档的得分详情，这里用split截取总分
			scoreExplain = isearcher.explain(query, hits[i].doc).toString()
					.split(" ", 2);
			String scores = scoreExplain[0];
			// assertEquals("Thisis the text to be indexed.",
			// hitDoc.get("fieldname"));
			System.out.println(hitDoc.get("fieldname") + "\n*score* " + scores);
		}
		ireader.close();
		directory.close();
	}
*/}
