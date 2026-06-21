package co.buzmo.reflex.web;

import co.buzmo.reflex.content.Article;
import co.buzmo.reflex.content.Articles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Server-rendered guide pages (Thymeleaf) with JSON-LD for SEO / AI-search. */
@Controller
public class LearnController {

    private final ObjectMapper mapper;

    @Value("${app.base-url}")
    private String baseUrl;

    public LearnController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @GetMapping("/learn")
    public String hub(Model model) {
        model.addAttribute("articles", Articles.ALL);
        model.addAttribute("baseUrl", baseUrl);
        return "learn-hub";
    }

    @GetMapping("/learn/{slug}")
    public String article(@PathVariable String slug, Model model) {
        Article a = Articles.bySlug(slug);
        if (a == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such guide");
        }
        model.addAttribute("a", a);
        model.addAttribute("articles", Articles.ALL);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("jsonLd", buildJsonLd(a));
        return "learn-article";
    }

    /** Builds an Article + FAQPage JSON-LD array. Jackson handles all escaping. */
    private String buildJsonLd(Article a) {
        String url = baseUrl + "/learn/" + a.slug();

        Map<String, Object> article = new LinkedHashMap<>();
        article.put("@context", "https://schema.org");
        article.put("@type", "Article");
        article.put("headline", a.title());
        article.put("description", a.metaDescription());
        article.put("datePublished", a.updated());
        article.put("dateModified", a.updated());
        article.put("mainEntityOfPage", url);
        article.put("author", Map.of("@type", "Organization", "name", "Reflex"));

        List<Map<String, Object>> questions = new ArrayList<>();
        for (Article.Faq f : a.faqs()) {
            questions.add(Map.of(
                    "@type", "Question",
                    "name", f.question(),
                    "acceptedAnswer", Map.of("@type", "Answer", "text", f.answer())
            ));
        }
        Map<String, Object> faqPage = new LinkedHashMap<>();
        faqPage.put("@context", "https://schema.org");
        faqPage.put("@type", "FAQPage");
        faqPage.put("mainEntity", questions);

        try {
            return mapper.writeValueAsString(List.of(article, faqPage));
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }
}
