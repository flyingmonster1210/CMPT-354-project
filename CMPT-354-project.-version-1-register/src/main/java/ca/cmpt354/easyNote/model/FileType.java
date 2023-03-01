package ca.cmpt354.easyNote.model;

import java.io.*;
import java.util.HashMap;

public class FileType {
    private static byte[] file;
    private static HashMap<String, String> headFile = new HashMap<>();

    public FileType(byte[] file) {
        this.file = file;
    }

    public FileType(File file) {
        int length = (int)file.length();
        this.file = new byte[length];
        InputStream read;

        //read image and store initial data into filedata
        try{
            read = new FileInputStream(file);
            read.read(this.file,0,this.file.length);
        }catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    static {
        headFile.put("FFD8FF", "jpg");
        headFile.put("89504E47", "png");
        headFile.put("47494638", "gif");
        headFile.put("49492A00", "tif");
        headFile.put("41433130", "dwg");
        headFile.put("38425053", "psd");
        headFile.put("7B5C727466", "rtf");
        headFile.put("3C3F786D6C", "xml");
        headFile.put("68746D6C3E", "html");
        headFile.put("44656C69766572792D646174653A", "eml");
        headFile.put("D0CF11E0", "doc");
        headFile.put("5374616E64617264204A", "mdb");
        headFile.put("252150532D41646F6265", "ps");
        headFile.put("255044462D312E", "pdf");
        headFile.put("504B0304", "docx");
        headFile.put("52617221", "rar");
        headFile.put("57415645", "wav");
        headFile.put("41564920", "avi");
        headFile.put("2E524D46", "rm");
        headFile.put("000001BA", "mpg");
        headFile.put("000001B3", "mpg");
        headFile.put("6D6F6F76", "mov");
        headFile.put("3026B2758E66CF11", "asf");
        headFile.put("4D546864", "mid");
        headFile.put("1F8B08", "gz");
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileType(){
        String fileType;
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 14; i++){
            fileType = Integer.toHexString(file[i]&0xff).toUpperCase();
            if(fileType.length() < 2)
                builder.append(0);
            builder.append(fileType);
        }
        fileType = builder.toString();

        /*for(int i = fileType.length(); i > 0; i -= 2){
            if(headFile.containsKey(fileType.substring(0,i))){
                return headFile.get(fileType.substring(0,i));
            }
        }

        return null;*/

        if(headFile.containsKey(fileType.substring(0,28))){
            System.out.println(fileType.substring(0,28));
            return headFile.get(fileType.substring(0,28));
        }
        else if(headFile.containsKey(fileType.substring(0,20))){
            System.out.println(fileType.substring(0,20));
            return headFile.get(fileType.substring(0,20));
        }
        else if(headFile.containsKey(fileType.substring(0,14))){
            System.out.println(fileType.substring(0,14));
            return headFile.get(fileType.substring(0,14));
        }
        else if(headFile.containsKey(fileType.substring(0,10))){
            System.out.println(fileType.substring(0,10));
            return headFile.get(fileType.substring(0,10));
        }
        else if(headFile.containsKey(fileType.substring(0,8))){
            System.out.println(fileType.substring(0,8));
            return headFile.get(fileType.substring(0,8));
        }
        else if(headFile.containsKey(fileType.substring(0,6))){
            System.out.println(fileType.substring(0,6));
            return headFile.get(fileType.substring(0,6));
        }
        else{
            return "txt";
        }
    }
}
