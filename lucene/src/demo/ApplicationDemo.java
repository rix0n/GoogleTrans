package demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class ApplicationDemo {

	private static String[] dataSource = { "搜狐主站", "搜狐视频", "搜狗输入法", "搜狗手机助手", "搜狗音乐", "搜搜引擎", "全新的正向迭代", "文法分词",
			"最细粒度切分算法", "开源工具包" };

	public static void createIndex() {

		// 实例化IKAnalyzer分词器
		Analyzer analyzer = new IKAnalyzer();

		Directory directory = null;
		IndexWriter iwriter = null;
		try {
			// 建立内存索引对象
//			/usr/search/index/app
			directory = FSDirectory.open(new File("/usr/search/index/app"));

			// 配置IndexWriterConfig
			IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_34, analyzer);
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			iwriter = new IndexWriter(directory, iwConfig);
			// 写入索引
			for (int i = 0; i < dataSource.length; i++) {
				Document doc = new Document();
				doc.add(new Field("ID", i + "", Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field("CONTENT", dataSource[i], Field.Store.YES, Field.Index.ANALYZED));
				iwriter.addDocument(doc);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (iwriter != null) {
				try {
					iwriter.close();
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

	public static void queryIndex(String keyword) {

		// 实例化IKAnalyzer分词器
		Analyzer analyzer = new IKAnalyzer();

		IndexReader ireader = null;
		IndexSearcher isearcher = null;
		try {
			
			// 搜索过程**********************************
			// 实例化搜索器
//			/usr/search/index/app
			FSDirectory directory = FSDirectory.open(new File("/usr/search/index/app"));
			ireader = IndexReader.open(directory);
			isearcher = new IndexSearcher(ireader);

			System.out.println("keyword:" + keyword);

			// 使用QueryParser查询分析器构造Query对象
			QueryParser qp = new MultiFieldQueryParser(Version.LUCENE_34, new String[]{"CONTENT"}, analyzer);
			qp.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = qp.parse(keyword);

			// 搜索相似度最高的5条记录
			TopDocs topDocs = isearcher.search(query, 1000, Sort.RELEVANCE);
			System.out.println("hit：" + topDocs.totalHits);
			// 输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < topDocs.totalHits; i++) {
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				System.out.println("content:" + targetDoc.toString());
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
			if(isearcher!=null){
				try {
					isearcher.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void queryHighlightIndex(String keyword) {
		
		// 实例化IKAnalyzer分词器
		Analyzer analyzer = new IKAnalyzer();

		IndexReader ireader = null;
		IndexSearcher isearcher = null;
		try {
			
			// 搜索过程**********************************
			// 实例化搜索器
//			/usr/search/index/app
			FSDirectory directory = FSDirectory.open(new File("/usr/search/index/app"));
			ireader = IndexReader.open(directory);
			isearcher = new IndexSearcher(ireader);

			System.out.println("keyword:" + keyword);

			// 使用QueryParser查询分析器构造Query对象
			QueryParser qp = new MultiFieldQueryParser(Version.LUCENE_34, new String[]{"CONTENT"}, analyzer);
			qp.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = qp.parse(keyword);
			
			SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
			Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
			 
			// 搜索相似度最高的5条记录
			TopDocs topDocs = isearcher.search(query, 1000, Sort.RELEVANCE);
			System.out.println("hit：" + topDocs.totalHits);
			// 输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < topDocs.totalHits; i++) {
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				System.out.println("content:" + targetDoc.toString());
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
			if(isearcher!=null){
				try {
					isearcher.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void generateDic() {
		for (int i = 0; i < dataSource.length; i++) {
			String word = dataSource[i];
			Set<String> dicSet = splitWord(word);
			appendDic(dicSet, "");
		}
	}

	public static Set<String> splitWord(String word) {
		Set<String> dicSet = new HashSet<String>();
		for (int i = 0; i < word.length(); i++) {
			Set<String> tempSet = new HashSet<String>();
			String temp = word.substring(i, word.length());
			recursiveWord(temp, tempSet);
			dicSet.addAll(tempSet);
		}
		
//		StringBuffer buffer = new StringBuffer(word);
//		String rword = buffer.reverse().toString();
//		for (int i = 0; i < rword.length(); i++) {
//			Set<String> tempSet = new HashSet<String>();
//			String temp = rword.substring(i, rword.length());
//			recursiveWord(temp, tempSet);
//			dicSet.addAll(tempSet);
//		}
		
		for (int i = 0; i < word.length(); i++) {
			Set<String> tempSet = new HashSet<String>();
			String temp = word.substring(0, word.length()-i);
			recursiveWord(temp, tempSet);
			dicSet.addAll(tempSet);
		}
		
		return dicSet;
	}

	public static void recursiveWord(String word, Set<String> dicList) {
		if (word.length() == 1) {
			dicList.add(word);
			return;
		}
		for (int i = 0; i < word.length(); i++) {
			dicList.add(word.substring(i, word.length()));
		}
		recursiveWord(word.substring(1, word.length()), dicList);
	}
	
	public static void nullFile(String filePath) {
		String FileContent = "";
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(FileContent);
			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 */
	public static boolean nullDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = nullDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	public static void appendDic(Set<String> dicList, String filePath) {
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath, true);
			osw = new OutputStreamWriter(fos, "UTF-8");

			for (Iterator<String> it = dicList.iterator(); it.hasNext();) {
				String dic = it.next();
				osw.write(dic + System.getProperty("line.separator"));
			}

			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
//		File dir = new File("D:/temporary/index");
//		nullDir(dir);
		createIndex();
		queryIndex("搜");
		
//		Set<String> splitWord = splitWord("搜狗浏览器");
//		for (Iterator it = splitWord.iterator(); it.hasNext();) {
//			String string = (String) it.next();
//			System.out.println(string);
//		}
		
	}

}
