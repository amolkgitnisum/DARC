package org.hac.drc.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hac.drc.core.ConcurrentProcessor;
import org.hac.drc.core.FileSplitter;
import org.hac.drc.core.ProcessingWorker;
import org.hac.drc.core.Worker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

@Repository
public class DarcDaoImpl implements DarcDao {

	private static final String CONNECTION = "Connection";
	private static final String ETAG = "ETag";
	private static final String Last_Modified = "Last-Modified";
	private static final String accept_encoding = "accept-encoding";
	private static final String vary = "vary";
	private static final String cache_control = "Cache-Control";
	private static final String status = "status";
	
	@Override
	public String generateReport() {
		// TODO Auto-generated method stub
		String status="success";
		return status;
	}
	
	public String getAlexaRanking(String url) {



		StringBuffer result = new StringBuffer();

		URL domainUrl;

		try {

		domainUrl = new URL(url);

		String domainName = domainUrl.getHost();


		URL urlFinal = new URL("https://www.alexa.com/siteinfo/" + domainName.replaceAll("wwww.", ""));



		URLConnection con = urlFinal.openConnection();

		InputStream is = con.getInputStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		LineNumberReader lnr = new LineNumberReader(br);

		int globalRankNumber = 0;

		int localRankNumber = 0;

		String areaName = "";

		for (String line = null; (line = lnr.readLine()) != null;) {



		if (line.contains("title='Global rank icon'")) {

		globalRankNumber = lnr.getLineNumber() + 2;

		}



		if (line.contains("<img class='img-inline ' src='/images/flags/")) {

		StringBuffer sb = new StringBuffer(line);

		areaName = line.substring((sb.indexOf("title='") + 7), sb.indexOf("Flag'")).trim();

		localRankNumber = lnr.getLineNumber() + 1;

		}



		if (lnr.getLineNumber() == globalRankNumber) {

		result.append(" Global Rank: " + line.replaceAll("</strong>", "").trim());

		System.out.println("Execution.main()     Global Rank: " + line.replaceAll("</strong>", "").trim());

		}

		if (lnr.getLineNumber() == localRankNumber) {

		result.append(" @  Rank in  " + areaName + " : " + line.replaceAll("</strong>", "").trim());

		System.out.println(

		"Execution.main()     " + areaName + " Rank: " + line.replaceAll("</strong>", "").trim());

		}

		}

		} catch (MalformedURLException e) {

		e.printStackTrace();

		} catch (IOException e) {

		e.printStackTrace();

		}



		return result.toString();



		}

	public Map<String, List<String>> checkHeaders(List<String> urls) throws Exception{
		
		Map<String, List<String>> parentMap = new HashMap<>();
		parentMap.put(CONNECTION, new ArrayList<>());
		parentMap.put(ETAG, new ArrayList<>());
		parentMap.put(Last_Modified, new ArrayList<>());
		parentMap.put(vary, new ArrayList<>());
		parentMap.put(cache_control, new ArrayList<>());
		parentMap.put(status, new ArrayList<>());
		parentMap.put(accept_encoding, new ArrayList<>());
		
		FileSplitter splitter = new FileSplitter();
		List<List<String>> chunkLists = splitter.splitFile(urls);
		List<Worker> workersList = new ArrayList<>();
		for (List<String> list : chunkLists) {
			workersList.add(new ProcessingWorker(list));
		}
		
		List<Object> responses = ConcurrentProcessor.processWorkers(workersList, workersList.size());
		
		responses.forEach(obj -> {
			Map<String, List<String>> defaulterMap = (Map<String, List<String>>)obj;
			
			List<String> connectionList = parentMap.get(CONNECTION);
			List<String> eTagList = parentMap.get(ETAG);
			List<String> lastModifiedList = parentMap.get(Last_Modified);
			List<String> acceptEncodingList = parentMap.get(accept_encoding);
			List<String> varyList = parentMap.get(vary);
			List<String> cacheControlList = parentMap.get(cache_control);
			List<String> statusList = parentMap.get(status);
			
			connectionList.addAll(defaulterMap.get(CONNECTION));
			eTagList.addAll(defaulterMap.get(ETAG));
			lastModifiedList.addAll(defaulterMap.get(Last_Modified));
			acceptEncodingList.addAll(defaulterMap.get(accept_encoding));
			varyList.addAll(defaulterMap.get(vary));
			cacheControlList.addAll(defaulterMap.get(cache_control));
			statusList.addAll(defaulterMap.get(status));
			
			parentMap.put(CONNECTION, connectionList);
			parentMap.put(ETAG, eTagList);
			parentMap.put(Last_Modified, lastModifiedList);
			parentMap.put(vary, varyList);
			parentMap.put(cache_control, cacheControlList);
			parentMap.put(status, statusList);
			parentMap.put(accept_encoding, acceptEncodingList);
			
		});
		System.out.println(responses);
		
		return parentMap;
	}
	
	public Map getfinalresult(String url) throws Exception
	{
		//String url = "https://www.macys.com/";
       // print("Fetching %s...", url);
		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        long webpagesize=0;;
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");
        HashMap responsemap=new HashMap();
        ArrayList<HashMap<String,String>> imageal=new ArrayList<HashMap<String,String>>();
        ArrayList<HashMap<String,String>> imageal1=new ArrayList<HashMap<String,String>>();
        ArrayList<HashMap<String,String>> imageal2=new ArrayList<HashMap<String,String>>();
        ArrayList<HashMap<String,String>> scriptfile=new ArrayList<HashMap<String,String>>();
        ArrayList<HashMap<String,String>> scriptfile1=new ArrayList<HashMap<String,String>>();
        ArrayList<HashMap<String,String>> cssfile=new ArrayList<HashMap<String,String>>();
        ArrayList<HashMap<String,String>> csstfile1=new ArrayList<HashMap<String,String>>();
        ArrayList<HashMap<String,String>> totalsal=new ArrayList<HashMap<String,String>>();
        ArrayList<String> listofurls=new ArrayList<String>();
        HashMap<String,String> totcountmap=new HashMap<String,String>();
        int noofimages=0;
        totcountmap.put("mediasize", String.valueOf(media.size()));
        totcountmap.put("importssize", String.valueOf(imports.size()));
        totcountmap.put("linkssize", String.valueOf(links.size()));
        //totalsal.add(totcountmap);
        System.out.println("webpagesize"+totcountmap.toString());
        //print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.tagName().equals("img"))
            {
               // print(" * %s: <%s> %sx%s (%s)",
                 //       src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
              String imagelink=src.attr("abs:src");
              noofimages=noofimages+1;
            	if(""!=imagelink)  
            	{
            	//System.out.println("imagefile"+src.attr("abs:src"));
                 //trim(src.attr("alt"), 20));
                  String size= getSizeifimage(src.attr("abs:src"));
                  //System.out.println(" ---> " + size + " kb");
                  int sizeinint=Integer.parseInt(size);
                 // System.out.println(" ---> " + sizeinint + " kb");
                  webpagesize=webpagesize+sizeinint;
                  if(sizeinint<15)
                  {
                	 // System.out.println("imageal"+imagelink);
                	  HashMap<String,String> imagemap=new HashMap<String,String>();
                	  imagemap.put("url", imagelink);
                	  imagemap.put("size", String.valueOf(sizeinint));
                	  imagemap.put("Status","Average");
                	  imageal.add(imagemap);
                	  //System.out.println("imageal"+imageal.toString());
                	  
                  }
                  else  if(sizeinint>15 && sizeinint<200)
                  {
                	 // System.out.println("imageal1"+imagelink);
                	  HashMap<String,String> imagemap=new HashMap<String,String>();
                	  imagemap.put("url", imagelink);
                	  imagemap.put("size", String.valueOf(sizeinint));
                	  imagemap.put("Status","Good");
                	  imageal1.add(imagemap);
                	  //System.out.println("imageal"+imageal1.toString());
                  }
                  else if(sizeinint>150)
                  {
                	 // System.out.println("imageal2"+imagelink);
                	  HashMap<String,String> imagemap=new HashMap<String,String>();
                	  imagemap.put("url", imagelink);
                	  imagemap.put("size", String.valueOf(sizeinint));
                	  imagemap.put("Status","Poor");
                	  imageal2.add(imagemap);
                  }
                  
            	}
            	
            }
            else
            {
            	if (src.tagName().equals("script")) 
            	{
            		String jsfile=src.attr("abs:src");
            		String size= getSizeifimage(jsfile);
            		int sizeinint=Integer.parseInt(size);
            		webpagesize=webpagesize+sizeinint;
    				if(jsfile.contains("min.js"))
    	            {
    					// print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
    					 HashMap<String,String> scriptmap=new HashMap<String,String>();
    					 scriptmap.put("url", jsfile);
    					 scriptmap.put("size", String.valueOf(sizeinint));
    					 scriptmap.put("Status","Minified");
    					 scriptfile.add(scriptmap);
    	            }
    				else
    				{
    					//print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
    					 HashMap<String,String> scriptmap=new HashMap<String,String>();
    					 scriptmap.put("url", jsfile);
    					 scriptmap.put("size", String.valueOf(sizeinint));
    					 scriptmap.put("Status","Minified");
    					 scriptfile1.add(scriptmap);
    				}
            		
            	}
               
            }
        }

       // print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
        	
            if(link.attr("abs:href").contains("min.css"))
            {
            	String csslink=link.attr("abs:href");
        		String size= getSizeifimage(csslink);
        		int sizeinint=Integer.parseInt(size);
        		webpagesize=webpagesize+sizeinint;
        		 HashMap<String,String> csstmap=new HashMap<String,String>();
        		 csstmap.put("url", csslink);
        		 csstmap.put("size", String.valueOf(sizeinint));
        		 csstmap.put("Status","Minified");
        		 cssfile.add(csstmap);
            }
            else
            {
            	//print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
            	String csslink=link.attr("abs:href");
        		String size= getSizeifimage(csslink);
        		int sizeinint=Integer.parseInt(size);
        		webpagesize=webpagesize+sizeinint;
        		 HashMap<String,String> csstmap=new HashMap<String,String>();
        		 csstmap.put("url", csslink);
        		 csstmap.put("size", String.valueOf(sizeinint));
        		 csstmap.put("Status","Not Minified");
        		 csstfile1.add(csstmap);
        		 
            }
        }
        
       // print("\nRequests: (%d)", links.size());
        for (Element link : links) {
            //print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        	String hrefurl=link.attr("abs:href");
        	if(null!=hrefurl || ""!=hrefurl)
        	{
        	listofurls.add(link.attr("abs:href"));
        	}
        }
        totcountmap.put("webpagesize", String.valueOf(webpagesize));
        totcountmap.put("alexaranking",getAlexaRanking(url));
        totcountmap.put("noOfImages",String.valueOf(noofimages));
        
        totalsal.add(totcountmap);
        responsemap.put("totals", totalsal);
        responsemap.put("imageslessthan15kb", imageal);
        responsemap.put("imageslessthan150kb", imageal1);
        responsemap.put("imagesgreaterthan150kb", imageal2);
        responsemap.put("minifiedscript", scriptfile);
        responsemap.put("Notminifiedscript", scriptfile1);
        responsemap.put("minifiedcss", cssfile);
        responsemap.put("Notminifiedcss", csstfile1);
        responsemap.put("headers", checkHeaders(listofurls));
        System.out.println(responsemap.toString());
		
		return responsemap;
	}
	
	private  String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
    
    public  String getSizeifimage(String link)
    {
    	NumberFormat nf = new DecimalFormat();
        nf.setMaximumFractionDigits(2);
        String size1=null;
    	try
    	{
    		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
    		final URL url = new URL(link);
            final long size = url.openConnection().getContentLength();
            
            size1=nf.format(size/1024);
    	}
    	catch(Exception e)
    	{
    		
    	}
    	
    	return size1;
    }
}
