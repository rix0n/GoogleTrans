package demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * IKAnalyzer 示例 2012-3-2
 * 
 * 以下是结合Lucene3.4 API的写法
 * 
 */
public class IKAnalyzerDemo {
	
	public static void main(String[] args) {
		// Lucene Document的域名
		String fieldName = "text";
		// 检索内容
//		String text = "IK Analyzer是一个结合词典分词和文法分词的中文分词开源工具包。它使用了全新的正向迭代最细粒度切分算法。";
		String text = "搜狐视频";
		String[] dataSource = {"搜狐主站", "搜狐视频", "搜狗输入法", "搜狗手机助手","搜狗音乐",
				"搜搜引擎","全新的正向迭代","文法分词","最细粒度切分算法","开源工具包"};

		// 实例化IKAnalyzer分词器
		Analyzer analyzer = new IKAnalyzer();

		Directory directory = null;
		IndexWriter iwriter = null;
		IndexReader ireader = null;
		IndexSearcher isearcher = null;
		try {
			// 建立内存索引对象
			directory = new RAMDirectory();

			// 配置IndexWriterConfig
			IndexWriterConfig iwConfig = new IndexWriterConfig(
					Version.LUCENE_34, analyzer);
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			iwriter = new IndexWriter(directory, iwConfig);
			// 写入索引
			for (int i = 0; i < dataSource.length; i++) {
				Document doc = new Document();
				doc.add(new Field("ID", i+"", Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field(fieldName, dataSource[i], Field.Store.YES,
						Field.Index.ANALYZED));
				iwriter.addDocument(doc);
			}
			iwriter.close();

			// 搜索过程**********************************
			// 实例化搜索器
			ireader = IndexReader.open(directory);
			isearcher = new IndexSearcher(ireader);

//			String keyword = "中文分词工具包";
//			String keyword = "输入";
			String keyword = "搜";
			System.out.println("keyword:" + keyword);
			
			// 使用QueryParser查询分析器构造Query对象
			QueryParser qp = new QueryParser(Version.LUCENE_34, fieldName,
					analyzer);
			qp.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = qp.parse(keyword);

			// 搜索相似度最高的5条记录
			TopDocs topDocs = isearcher.search(query, 10, Sort.RELEVANCE);
			System.out.println("hit：" + topDocs.totalHits);
			// 输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < topDocs.totalHits; i++) {
				System.out.println( scoreDocs[i].score );
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				System.out.println("score:" + "; content:"
						+ targetDoc.toString());
			}

		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (ireader != null) {
				try {
					ireader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (directory != null) {
				try {
					directory.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
