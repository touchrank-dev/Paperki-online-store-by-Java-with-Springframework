package com.kushnir.paperki.service.util;

import org.springframework.util.Assert;

public class Transliterator {

    public static String cyr2lat(char ch){
        ch = Character.toLowerCase(ch);
        switch (ch){
            case 'а': return "a";
            case 'б': return "b";
            case 'в': return "v";
            case 'г': return "g";
            case 'д': return "d";
            case 'е': return "e";
            case 'ё': return "je";
            case 'ж': return "zh";
            case 'з': return "z";
            case 'и': return "i";
            case 'й': return "y";
            case 'к': return "k";
            case 'л': return "l";
            case 'м': return "m";
            case 'н': return "n";
            case 'о': return "o";
            case 'п': return "p";
            case 'р': return "r";
            case 'с': return "s";
            case 'т': return "t";
            case 'у': return "u";
            case 'ф': return "f";
            case 'х': return "kh";
            case 'ц': return "c";
            case 'ч': return "ch";
            case 'ш': return "sh";
            case 'щ': return "jsh";
            case 'ъ': return "";
            case 'ы': return "ih";
            case 'ь': return "";
            case 'э': return "eh";
            case 'ю': return "ju";
            case 'я': return "ja";
            case ' ': return "-";
            /*case ',': return "";
            case '.': return "";
            case '"': return "";
            case '\'': return "";
            case '\\': return "";
            case '|' : return "";
            case '/' : return "";*/
            case '0': return "0";
            case '1': return "1";
            case '2': return "2";
            case '3': return "3";
            case '4': return "4";
            case '5': return "5";
            case '6': return "6";
            case '7': return "7";
            case '8': return "8";
            case '9': return "9";
            default: return "";
        }
    }

    public static String cyr2lat(String s){
        Assert.notNull(s, "Пустая срока");
		StringBuilder sb = new StringBuilder(s.length()*2);
		for(char ch: s.toCharArray()){
			sb.append(cyr2lat(ch));
		}
		return sb.toString();
	}

}
