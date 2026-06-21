package co.buzmo.reflex.web;

import co.buzmo.reflex.content.Article;
import co.buzmo.reflex.content.Articles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** sitemap.xml and llms.txt so search engines and AI crawlers can find the guides. */
@RestController
public class SeoController {

    @Value("${app.base-url}")
    private String baseUrl;

    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String sitemap() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");
        sb.append(url(baseUrl + "/", null));
        sb.append(url(baseUrl + "/learn", null));
        for (Article a : Articles.ALL) {
            sb.append(url(baseUrl + "/learn/" + a.slug(), a.updated()));
        }
        sb.append("</urlset>\n");
        return sb.toString();
    }

    private String url(String loc, String lastmod) {
        StringBuilder sb = new StringBuilder("  <url><loc>").append(loc).append("</loc>");
        if (lastmod != null) {
            sb.append("<lastmod>").append(lastmod).append("</lastmod>");
        }
        sb.append("</url>\n");
        return sb.toString();
    }

    @GetMapping(value = "/llms.txt", produces = MediaType.TEXT_PLAIN_VALUE)
    public String llms() {
        StringBuilder sb = new StringBuilder();
        sb.append("# Reflex\n\n");
        sb.append("> A free reaction-time and aim-training game with a global leaderboard.\n\n");
        sb.append("## Guides\n\n");
        for (Article a : Articles.ALL) {
            sb.append("- [").append(a.title()).append("](").append(baseUrl)
              .append("/learn/").append(a.slug()).append("): ")
              .append(a.metaDescription()).append("\n");
        }
        return sb.toString();
    }
}
