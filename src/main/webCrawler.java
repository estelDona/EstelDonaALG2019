package main;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.swing.text.Document;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;

public class webCrawler {
	
	 private HashSet<String> links;
	 
	    public webCrawler() {
	        links = new HashSet<String>();
	       
	    }

	    public void getPageLinks(String URL,int depth) {
	        //4. Check if you have already crawled the URLs 
	        //(we are intentionally not checking for duplicate content in this example)
	    	if(depth==0) {
	    		return;
	    	}
	        if (!links.contains(URL)) {
	            try {
	                //4. (i) If not add it to the index
	                if (links.add(URL)) {
	                    System.out.println(URL);
	                }

	                //2. Fetch the HTML code
	                Document document = (Document) Jsoup.connect(URL).get();
	                //3. Parse the HTML to extract links to other URLs
	                org.jsoup.select.Elements linksOnPage = ((org.jsoup.nodes.Element) document).select("a[href]");

	                //5. For each extracted URL... go back to Step 4.
	                for (org.jsoup.nodes.Element page : linksOnPage) {
	                    getPageLinks(((Node) page).attr("abs:href"),depth-1);
	                }
	            } catch (IOException e) {
	                System.err.println("For '" + URL + "': " + e.getMessage());
	            }
	        }
	    }

	    public static void main(String[] args) {
	    	
	      webCrawler webCrawler= new webCrawler();
	      
	      webCrawler.getPageLinks(args[0],Integer.parseInt(args[1]));
	      
	    FileWriter fw = null;
	    
		try {
			fw = new FileWriter(args[2]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	      
	      for (String s : webCrawler.links) {
	    	  try {
				fw.write(s+";");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
	    	}
           
          try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	        
	    }

}
