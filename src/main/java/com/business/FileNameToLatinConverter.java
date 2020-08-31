package com.business;

import java.util.HashMap;
import java.util.Map;

public class FileNameToLatinConverter {

    private final Map<String, String> CYRILLIC_TO_LATIN_TABLE;

    FileNameToLatinConverter() {
        Map<String, String> table = new HashMap<>();
        table.put("а", "a");
        table.put("б", "b");
        table.put("в", "v");
        table.put("г", "g");
        table.put("д", "d");
        table.put("е", "e");
        table.put("ё", "yo");
        table.put("ж", "zh");
        table.put("з", "z");
        table.put("и", "i");
        table.put("й", "y");
        table.put("к", "k");
        table.put("л", "l");
        table.put("м", "m");
        table.put("н", "n");
        table.put("о", "o");
        table.put("п", "p");
        table.put("р", "r");
        table.put("с", "s");
        table.put("т", "t");
        table.put("у", "u");
        table.put("ф", "f");
        table.put("х", "h");
        table.put("ц", "ts");
        table.put("ч", "ch");
        table.put("ш", "sh");
        table.put("щ", "sh'");
        table.put("ъ", "");
        table.put("ы", "i");
        table.put("ь", "'");
        table.put("э", "e");
        table.put("ю", "yu");
        table.put("я", "ya");

        this.CYRILLIC_TO_LATIN_TABLE = table;
    }

    /**
     * Method replaces all Cyrillic characters to Latin
     *
     * @param fileName
     * @return latinFileName - only with latin chars
     */
    public String convert(String fileName) {
        String fileNameLowerCase = fileName.toLowerCase();

        StringBuilder resultName = new StringBuilder();

        for (int i = 0; i < fileName.length(); i++) {
            String strSymbol = String.valueOf(fileNameLowerCase.charAt(i));

            if (CYRILLIC_TO_LATIN_TABLE.containsKey(strSymbol)) {
                String substitution = CYRILLIC_TO_LATIN_TABLE.get(strSymbol);
                resultName.append(substitution);
                continue;
            }
            resultName.append(strSymbol);
        }
        return resultName.toString();
    }

}
