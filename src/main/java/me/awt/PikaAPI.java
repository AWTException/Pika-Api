package me.awt;

import com.google.gson.*;
import me.awt.enums.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PikaAPI {
    //offset = Which position the leaderboard starts from. (For example, if offset is 5, the leaderboard starts from #6. If it's 0, the leaderboard starts from #1)
    //limit = Which position the leaderboard ends at. (For example, if the limit 10, it will show the top 10 players)

    public static JsonObject getProfile(String playerName){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("https://stats.pika-network.net/api/profile/" + playerName);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            
            int returnCode = con.getResponseCode();
            if(returnCode != 200){
                return JsonParser.parseString("{\n" +
                        "  \"code\": "+returnCode+"\n" +
                        "}").getAsJsonObject();
            }
            
            InputStream is = con.getInputStream();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            }
        }catch (Exception e){}
        return JsonParser.parseString(sb.toString()).getAsJsonObject();
    }
    public static JsonObject getGamemodeStats(Gamemode gamemode, String playerName, Interval interval, Mode mode){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("https://stats.pika-network.net/api/profile/" + playerName +"/leaderboard?type=" + gamemode.toString() +
                    "&interval=" + interval.toString() + "&mode=" + mode.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            
            int returnCode = con.getResponseCode();
            if(returnCode != 200){
                return JsonParser.parseString("{\n" +
                        "  \"code\": "+returnCode+"\n" +
                        "}").getAsJsonObject();
            }
    
            InputStream is = con.getInputStream();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            }
        }catch (Exception e){}
        return JsonParser.parseString(sb.toString()).getAsJsonObject();
    }
    public static JsonObject getLeaderBoardStats(Gamemode gamemode, Interval interval, Mode mode, Stat stat, int offset, int limit){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("https://stats.pika-network.net/api/leaderboards?type="
                    +gamemode.toString()+"&stat="+stat.toString()+"&interval="+interval.toString()+"&mode="+mode.toString()+"&offset="+offset+"&limit=" + limit);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
    
            int returnCode = con.getResponseCode();
            if(returnCode != 200){
                return JsonParser.parseString("{\n" +
                        "  \"code\": "+returnCode+"\n" +
                        "}").getAsJsonObject();
            }
            
            InputStream is = con.getInputStream();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            }
        }catch (Exception e){}
        return JsonParser.parseString(sb.toString()).getAsJsonObject();
    }
    
    
    
    
    
    public static ArrayList<String> getStaffMembers(){//TODO: return player stats
        ArrayList<String> al = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("https://pika-network.net/staff/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8");
//            con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            con.setRequestProperty("Accept-Language", "el-GR,el;q=0.8,en-US;q=0.5,en;q=0.3");
            con.setRequestProperty("Connection", "keep-alive");
            con.setRequestProperty("Cookie", "xf_from_search=bing; xf_csrf=bq4uoqin9j0yXfHO; _ga_LRYLYJ15WQ=GS1.1.1707322161.2.1.1707325161.0.0.0; _ga=GA1.1.112898027.1707318815; _pk_ref.3.36a7=%5B%22%22%2C%22%22%2C1707322162%2C%22https%3A%2F%2Fwww.bing.com%2F%22%5D; _pk_id.3.36a7=2695587e6d959f2b.1707318816.; _pk_ses.3.36a7=1; xf_notice_dismiss=-1");
            con.setRequestProperty("DNT", "1");
            con.setRequestProperty("Host", "pika-network.net");
            con.setRequestProperty("Referer", "https://pika-network.net/rules/");
            con.setRequestProperty("Sec-Fetch-Dest", "document");
            con.setRequestProperty("Sec-Fetch-Mode", "navigate");
            con.setRequestProperty("Sec-Fetch-Site", "same-origin");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0");
            con.setRequestMethod("GET");
            InputStream is = con.getInputStream();
            
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                
                boolean foundmemberDiv = false;
                int divsSinceThen = 0;
                
                int startInd = 0;
                int endInd = 0;
                
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    if(line.contains("<div class=\"member\">")){
                        startInd = sb.length();
                        foundmemberDiv = true;
                    }
                    if(foundmemberDiv){
                        if(line.contains("<div")){
                            divsSinceThen++;
                        }
                        if(line.contains("</div")){
                            divsSinceThen--;
                            if(divsSinceThen == 0) {
                                endInd = sb.length();
                                foundmemberDiv = false;
                                String memberPart = sb.subSequence(startInd, endInd).toString();
                                
                                PROCESS_MEMBER:{
                                    int[] da = new int[4];
                                    da[0] = memberPart.indexOf("username--staff") + "username--staff".length();
                                    da[1] = memberPart.indexOf("username--admin") + "username--admin".length();
                                    da[2] = memberPart.indexOf("username--moderator") + "username--moderator".length();
                                    da[3] = memberPart.indexOf("username--style24") + "username--style24".length();
                                    int in = 0;
                                    for(char iWillMakeThisFunctionBetter_IPromise = 0; iWillMakeThisFunctionBetter_IPromise < 4; iWillMakeThisFunctionBetter_IPromise++){
                                        if(da[iWillMakeThisFunctionBetter_IPromise] > in){
                                            in = da[iWillMakeThisFunctionBetter_IPromise];
                                        }
                                    }
                                    in+=2;
                                    
                                    char[] chars = new char[memberPart.length() - in];
                                    memberPart.getChars(in, memberPart.length(), chars, 0);
                                    
                                    for(int i = 0; i < chars.length; i++){
//                                        if(chars[i] == '>'){
//                                            in+=(i - 1);
//                                        }
                                        if(chars[i] == '<'){
                                            String name = memberPart.substring(in, in+i);
                                            al.add(name);
                                            break;
                                        }
                                    }
//                                    for(int a =0; a<7;a++){
//                                        System.out.println();
//                                    }
                                }
                                
                            }
                        }
                    }
                }
            }
        }catch (Exception e){e.printStackTrace();}
        return al;
    }
    public static String beutifyJson(JsonObject json){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        String prettyJsonString = gson.toJson(json);
        return prettyJsonString;
    }
}
