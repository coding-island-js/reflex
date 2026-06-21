package co.buzmo.reflex.content;

import co.buzmo.reflex.content.Article.Faq;
import co.buzmo.reflex.content.Article.Section;

import java.util.List;

/** Static, hand-written guide content. AEO-structured: a direct answer first, then detail, then FAQ. */
public final class Articles {

    private Articles() {}

    public static final List<Article> ALL = List.of(
            new Article(
                    "what-is-a-good-reaction-time",
                    "What Is a Good Reaction Time?",
                    "A good visual reaction time is about 200-250 ms. Under 200 ms is excellent, and the average human reacts to something they see in roughly 250 ms.",
                    "A good reaction time to a visual signal is around 200-250 milliseconds (ms). The average person reacts to something they see in about 250 ms, trained gamers and athletes often hit 150-200 ms, and anything under 200 ms is considered excellent. Reaction time is simply the gap between a stimulus appearing and your body responding to it.",
                    List.of(
                            new Section("Fast vs slow, in numbers", List.of(
                                    "Under 150 ms: elite, the level of pro esports players and sprinters reacting to a starting gun.",
                                    "150-200 ms: excellent, faster than most people will ever measure.",
                                    "200-250 ms: good, and right around the average for a healthy adult.",
                                    "250-300 ms: a little below average, often a sign of fatigue or distraction.",
                                    "Over 300 ms: slow for a simple visual task, common when tired or multitasking."
                            )),
                            new Section("Why visual reaction is slower than it feels", List.of(
                                    "When a light changes, the signal has to reach your eye, travel to the visual cortex, get processed, trigger a motor command, and finally move a muscle. That whole chain rarely happens in under about 100-120 ms, which is the hard physical floor for humans.",
                                    "Sound and touch are slightly faster than vision because those signals reach the brain through shorter, quicker pathways."
                            )),
                            new Section("What changes your reaction time", List.of(
                                    "Age, sleep, caffeine, hydration, practice, and how warmed-up you are all move the number. The biggest swings come from sleep and focus: a tired, distracted person can easily be 50-100 ms slower than their rested best."
                            ))
                    ),
                    List.of(
                            new Faq("What is the average human reaction time?",
                                    "About 250 ms for a visual stimulus. Reaction to sound is a bit faster, around 170-200 ms."),
                            new Faq("Is 200 ms a good reaction time?",
                                    "Yes. 200 ms is a strong result that beats most people and is typical of gamers and athletes who train for it."),
                            new Faq("Can you improve your reaction time?",
                                    "Yes, with practice, sleep, and warm-ups you can usually shave 10-30 ms, though genetics set a floor around 100-120 ms.")
                    ),
                    "2026-06-20"
            ),
            new Article(
                    "average-reaction-time-by-age",
                    "Average Reaction Time by Age",
                    "Reaction time peaks in your early-to-mid 20s (around 200-250 ms) and slows gradually with age. Here is how it changes across the lifespan.",
                    "Human reaction time is fastest in the early-to-mid 20s, averaging about 200-250 ms for a visual cue, then slows gradually by roughly 2-6 ms per decade after age 30. Children are slower than young adults, and the decline becomes more noticeable after about age 60.",
                    List.of(
                            new Section("Reaction time across the lifespan", List.of(
                                    "Children and teens are still developing and tend to be slower than young adults. Reaction time then sharpens to its lifetime best somewhere between 18 and 25.",
                                    "From the 30s onward it creeps upward slowly. The change per decade is small, but it adds up: a typical 60-year-old is noticeably slower than they were at 25."
                            )),
                            new Section("How much does age really slow you down?", List.of(
                                    "Less than most people fear. The per-decade increase is only a few milliseconds for simple tasks. Lifestyle, fitness, and sleep often matter more than age alone, which is why a fit 50-year-old can out-react a tired 25-year-old."
                            )),
                            new Section("Staying fast as you age", List.of(
                                    "Regular aerobic exercise, strength training, good sleep, and staying mentally engaged all help preserve reaction speed. Reflex and aim drills also keep the stimulus-to-response pathway sharp."
                            ))
                    ),
                    List.of(
                            new Faq("At what age is reaction time fastest?",
                                    "Reaction time is generally fastest between ages 18 and 25, then declines slowly."),
                            new Faq("Does reaction time slow with age?",
                                    "Yes, but only gradually, by roughly 2-6 ms per decade after 30 for simple visual tasks."),
                            new Faq("Can older adults improve their reaction time?",
                                    "Yes. Exercise, sleep, and regular reaction practice can offset much of the age-related slowdown.")
                    ),
                    "2026-06-20"
            ),
            new Article(
                    "how-to-improve-reaction-time",
                    "How to Improve Your Reaction Time",
                    "Improve reaction time with daily reflex drills, better sleep, moderate caffeine, warm-ups, and lower input lag. Most people can cut 10-30 ms with consistent practice.",
                    "To improve your reaction time: practice reaction and aim drills regularly, prioritize sleep, use moderate caffeine, warm up before you perform, and cut input lag from your screen and mouse. Most people can shave 10-30 ms off their visual reaction time within a few weeks, though genetics set a hard floor around 100-120 ms.",
                    List.of(
                            new Section("1. Train with reaction drills", List.of(
                                    "Reaction speed is trainable. Short, daily sessions of reaction and aim drills teach your brain to fire the motor response faster. Consistency beats marathon sessions: a few focused minutes a day works better than an hour once a week."
                            )),
                            new Section("2. Sleep and recovery", List.of(
                                    "Sleep is the single biggest lever. One bad night can add 50 ms or more. Aim for 7-9 hours, and treat rest as part of training, not separate from it."
                            )),
                            new Section("3. Caffeine and timing", List.of(
                                    "Moderate caffeine (roughly one coffee) reliably speeds reaction time for a couple of hours. Too much causes jitter and overshooting, so more is not better."
                            )),
                            new Section("4. Cut latency you can control", List.of(
                                    "Your gear adds delay. A higher-refresh monitor (120 Hz+), a wired or low-latency mouse, and a warmed-up hand all reduce the time between the signal and your click. None of this changes your biology, but it removes milliseconds you are losing for free."
                            ))
                    ),
                    List.of(
                            new Faq("How long does it take to improve reaction time?",
                                    "Most people see measurable gains within 2-4 weeks of short, consistent daily practice."),
                            new Faq("Does caffeine improve reaction time?",
                                    "Yes, moderate caffeine speeds reaction time for a few hours. Excess caffeine can hurt it by causing jitter."),
                            new Faq("What is the fastest possible human reaction time?",
                                    "Around 100-120 ms for a simple visual stimulus. That is the physical floor set by nerve and processing speed.")
                    ),
                    "2026-06-20"
            )
    );

    public static Article bySlug(String slug) {
        return ALL.stream().filter(a -> a.slug().equals(slug)).findFirst().orElse(null);
    }
}
