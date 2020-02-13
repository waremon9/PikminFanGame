/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

/**
 *
 * @author verhi
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileUtil {
    
    public static List<String> readResourceLines(String name) {
        try {
            LinkedList<String> lines=new LinkedList<String>();
            BufferedReader rd=new BufferedReader(new InputStreamReader(FileUtil.class.getResourceAsStream(name)));
            String line;
            while((line=rd.readLine())!=null) {
                lines.add(line);
            }
            rd.close();
            return lines;
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static ArrayList<String> readResourceLinesAsArrayList(String name) {
        ArrayList<String> list=new ArrayList<String>();
        list.addAll(readResourceLines(name));
        return list;
    }
}
