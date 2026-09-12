package access_modifiers.assigment_problems;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Problems 1 & 2: Access Checker & Subclass Reach
 * Implements access reach classification according to Java visibility rules,
 * modifier aggregation, and human-readable context formatting.
 */
public class AccessChecker {

    /**
     * Classifies access as "ALLOWED" or "DENIED" based on field modifier and accessor context.
     * Contexts supported:
     * - SAME_CLASS
     * - SAME_PACKAGE
     * - DIFFERENT_PACKAGE
     * - SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE
     * - SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE
     */
    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) {
            return "DENIED";
        }

        String mod = fieldModifier.trim().toLowerCase();
        String ctx = accessorContext.trim().toUpperCase();

        switch (mod) {
            case "public":
                return "ALLOWED";

            case "protected":
                if ("SAME_CLASS".equals(ctx) || "SAME_PACKAGE".equals(ctx)) {
                    return "ALLOWED";
                }
                if ("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equals(ctx)) {
                    return "ALLOWED";
                }
                // "DIFFERENT_PACKAGE" or "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
                return "DENIED";

            case "default":
                if ("SAME_CLASS".equals(ctx) || "SAME_PACKAGE".equals(ctx)) {
                    return "ALLOWED";
                }
                return "DENIED";

            case "private":
                if ("SAME_CLASS".equals(ctx)) {
                    return "ALLOWED";
                }
                return "DENIED";

            default:
                return "DENIED";
        }
    }

    /**
     * Problem 2: Turns underscore-separated code into a readable title-cased sentence.
     * Example: "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE" -> "Subclass Different Package Own Type"
     */
    public static String describeContext(String accessorContext) {
        if (accessorContext == null || accessorContext.trim().isEmpty()) {
            return "";
        }
        String[] words = accessorContext.trim().split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            if (w.isEmpty()) continue;
            if (sb.length() > 0) sb.append(" ");
            sb.append(Character.toUpperCase(w.charAt(0)));
            if (w.length() > 1) {
                sb.append(w.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * Problem 1: Groups results per modifier in fixed order: private, default, protected, public.
     * Output format:
     * "private: X allowed / Y denied | default: X allowed / Y denied | protected: X allowed / Y denied | public: X allowed / Y denied"
     */
    public static String summarizeByModifier(String[][] attempts) {
        Map<String, int[]> stats = new LinkedHashMap<>();
        stats.put("private", new int[]{0, 0});
        stats.put("default", new int[]{0, 0});
        stats.put("protected", new int[]{0, 0});
        stats.put("public", new int[]{0, 0});

        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt == null || attempt.length < 2) continue;
                String mod = attempt[0] != null ? attempt[0].trim().toLowerCase() : "";
                String ctx = attempt[1];
                if (stats.containsKey(mod)) {
                    String res = classifyAccess(mod, ctx);
                    if ("ALLOWED".equals(res)) {
                        stats.get(mod)[0]++;
                    } else {
                        stats.get(mod)[1]++;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Map.Entry<String, int[]> entry : stats.entrySet()) {
            if (count > 0) sb.append(" | ");
            sb.append(entry.getKey()).append(": ")
              .append(entry.getValue()[0]).append(" allowed / ")
              .append(entry.getValue()[1]).append(" denied");
            count++;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 1 & 2: AccessChecker Demos ===");
        System.out.println("classifyAccess(private, SAME_CLASS): " + classifyAccess("private", "SAME_CLASS")); // ALLOWED
        System.out.println("classifyAccess(protected, DIFFERENT_PACKAGE): " + classifyAccess("protected", "DIFFERENT_PACKAGE")); // DENIED
        System.out.println("classifyAccess(protected, SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE): " + classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")); // ALLOWED
        System.out.println("classifyAccess(protected, SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE): " + classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE")); // DENIED

        System.out.println("describeContext: " + describeContext("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));

        String[][] attempts = {
            {"private", "SAME_CLASS"},
            {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"},
            {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println("Summary: " + summarizeByModifier(attempts));
    }
}
