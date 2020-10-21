package com.mihey.json_xml_convertor;

import java.util.Scanner;

public class JsonXmlConverter {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        String s = sc.nextLine();
        String key = "";
        String value = "";
        String element = "";
        String content = "";


        if (s.matches("^<.*")) {
            int i = 1;
            while (s.charAt(i) != '>' && s.charAt(i) != '/') {
                sb.append(s.charAt(i));
                i++;
            }
            key = sb.toString();

            if (s.charAt(i) != '/') {
                i++;
                while (s.charAt(i) != '<') {
                    sb1.append(s.charAt(i));
                    i++;
                }
                value = sb1.toString();
                System.out.printf("{\"%s\" : \"%s\"}", key, value);
            } else {
                System.out.printf("{\"%s\" : null}", key);
            }


        } else {
            sb = new StringBuilder();
            sb1 = new StringBuilder();
            int i = 1;
            s = s.substring(1).trim();
            while (s.charAt(i) != '\"') {
                sb.append(s.charAt(i));
                i++;
            }
            element = sb.toString();
            i++;
            while (s.charAt(i) != '\"' && s.charAt(i) != '}') {
                i++;
            }
            if (s.charAt(i) == '\"') {
                i++;
                while (s.charAt(i) != '\"') {
                    sb1.append(s.charAt(i));
                    i++;
                }
                content = sb1.toString();
                System.out.printf("<%s>%s</%s>",element,content,element);
            } else {
                System.out.printf("<%s/>", element);
            }
        }

    }
}
