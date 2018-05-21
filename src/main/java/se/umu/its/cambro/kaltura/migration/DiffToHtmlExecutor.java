package se.umu.its.cambro.kaltura.migration;

import com.sksamuel.diffpatch.DiffMatchPatch;

import java.util.LinkedList;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

class DiffToHtmlExecutor {

    static String diffToHtml(String text1, String text2) {

        DiffMatchPatch diffMatchPatch = new DiffMatchPatch();

        LinkedList<DiffMatchPatch.Diff> diffs = diffMatchPatch.diff_main(text1, text2);

        diffMatchPatch.diff_cleanupSemantic(diffs);

        StringBuilder stringBuilder = new StringBuilder();

        for (DiffMatchPatch.Diff diff : diffs) {

            switch (diff.operation) {
                case EQUAL:
                    stringBuilder.append(escapeHtml4(diff.text));
                    break;
                case DELETE:
                    stringBuilder.append("<span style=\"background-color:red\">")
                                 .append(diff.text)
                                 .append("</span>");
                    break;
                case INSERT:
                    stringBuilder.append("<span style=\"background-color:green\">")
                                 .append(diff.text)
                                 .append("</span>");
                    break;
                default:
                    break;
            }
        }

        return stringBuilder.toString();
    }
}