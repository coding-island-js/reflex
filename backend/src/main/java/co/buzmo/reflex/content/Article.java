package co.buzmo.reflex.content;

import java.util.List;

/** A blog/guide article, structured answer-first for SEO and AI-search (AEO). */
public record Article(
        String slug,
        String title,
        String metaDescription,
        String answer,
        List<Section> sections,
        List<Faq> faqs,
        String updated
) {
    public record Section(String heading, List<String> paragraphs) {}

    public record Faq(String question, String answer) {}
}
