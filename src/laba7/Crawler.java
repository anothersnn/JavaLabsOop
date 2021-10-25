package laba7;

import java.net.*;
import java.util.*;

public class Crawler
{
    private HashMap <String, URLDepthPair> visited;
    private LinkedList <URLDepthPair> notVisited;
    private int maxDepth = 0;

    public Crawler(String URL, int maxDepth)
    {
        visited = new HashMap<>();
        notVisited = new LinkedList<>();
        notVisited.add(new URLDepthPair(URL,0));
        this.maxDepth = maxDepth;
    }

    public void run()
    {
        while (notVisited.size() > 0)
        {
            URLDepthPair link = notVisited.pop();
            if (visited.containsKey(link.getURL())) continue;
            visited.put(link.getURL(), link);
            System.out.println(link);
            if (link.getDepth() != maxDepth)
                findLinks(link);
        }

        System.out.println();
        System.out.print("Всего ссылок: " + visited.size());
    }

    private void findLinks(URLDepthPair link)
    {
        try
        {
            URL url = new URL(link.getURL());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            Scanner scanner = new Scanner(connection.getInputStream());

            while (scanner.findWithinHorizon("<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1", 0) != null)
            {
                String newURL = scanner.match().group(2);
                createNewLink(newURL, link);
            }
        }
        catch (Exception e)
        {
            System.err.println ("Error: " + e.getMessage());
        }
    }

    private void createNewLink(String newURL, URLDepthPair link)
    {
        if (newURL.startsWith("/"))
        {
            newURL = link.getURL() + newURL;
        }
        else if (!newURL.startsWith("http")) return;

        URLDepthPair newLink = new URLDepthPair(newURL, link.getDepth() + 1);
        notVisited.add(newLink);
    }

    public static void main(String[] args)
    {
        Crawler crawler = new Crawler("http://www.cs.caltech.edu/courses/cs11",2);
        crawler.run();
    }
}