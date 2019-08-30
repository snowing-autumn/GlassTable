package com.example.glasstable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.Connection.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.Cookie;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.*;



public class Internet {
    private static String myUrl="http://jwk.lzu.edu.cn/academic/j_acegi_security_check";
    private static String codeUrl="http://jwk.lzu.edu.cn/academic/getCaptcha.do";
    private static String userAgent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36";
    private static String captacha="";
    private static String userName="";
    private static String userPasswd="";
    private static Map<String,String> mapCookies;
    private static  HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    private static OkHttpClient client=new OkHttpClient.Builder().cookieJar(new CookieJar() {
        @Override
        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
            cookieStore.put(httpUrl.host(), list);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
            List<Cookie> cookies = cookieStore.get(httpUrl.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    }).build();

    public static void test()  throws IOException{
        client=new OkHttpClient();
        Request request=new Request.Builder().url("http://www.baidu.com").build();
        //Response response=client.newCall(request).execute();
    }

    public static Bitmap getCode(String path) throws IOException {
       /* client=new OkHttpClient();
        Request request=new Request.Builder().url(codeUrl).build();
        Response response=client.newCall(request).execute();
        InputStream inputStream=response.body().byteStream();
        Bitmap code= BitmapFactory.decodeStream(inputStream);*/

        Response responseForCode=Jsoup.connect(codeUrl).userAgent(userAgent).method(Method.GET).ignoreContentType(true).timeout(5 * 99).execute();
        mapCookies=responseForCode.cookies();
        InputStream inputStream=responseForCode.bodyStream();
        File codeFile=new File(path);
        if(codeFile.exists()){
            codeFile.delete();
            codeFile.createNewFile();
        }
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(codeFile));
        byte[] buf = new byte[1024];
        int size;
        while (-1 != (size = inputStream.read(buf))) {
            out.write(buf, 0, size);
        }
        out.close();
        inputStream.close();
        Bitmap code= BitmapFactory.decodeFile(path);
        return code;

    }

    public static Document Login() throws IOException{
        Response responseLogin=Jsoup.connect(myUrl).userAgent("Mozilla/5.0").timeout(5 * 200).cookies(mapCookies)
                .method(Method.POST).data("j_username",userName).data("j_password",userPasswd)
                .data("j_captcha",captacha).execute();
        mapCookies=responseLogin.cookies();
        Document document=Jsoup.connect("http://jwk.lzu.edu.cn/academic/listLeft.do").userAgent(userAgent)
                .timeout(5 * 200).cookies(mapCookies).get();
        System.out.println(document);
        return document;
    }

    public static Document findCourse(Document document) throws IOException{
        Element element=document.getElementById("li14");
        String courseLink=element.selectFirst("a").absUrl("href");
        System.out.println(courseLink);
        try{
            document=Jsoup.connect(courseLink).userAgent(userAgent)
                    .timeout(5 * 1000).cookies(mapCookies).get();}catch(Exception e){
            System.out.println("11");
        }
        return document;
    }

    public static boolean parseCourse(ArrayList<Course> courses, Document document){
        String tempCourseName;
        String tempTeacherName;
        String tempLocation;
        int tempStartWeek=0;
        int tempEndWeek=0;
        int tempIsOdd;
        int tempStratNumber=0;
        int tempEndNumber=0;
        int tempWeekDay;
        char odd;
        boolean isAdd;
        Elements tempInfos;
        Elements oriCourses=document.select(".infolist_tab").get(0).select(".infolist_common");
        for(Element tr:oriCourses){
            tempCourseName=tr.select("td").get(2).select("a").text();
            tempTeacherName=tr.select("td").get(3).select("a").text();
            tempInfos=tr.select("td").get(9).select("table").select("tbody").select("tr");
            for(Element info:tempInfos) {
                isAdd=false;
                tempLocation = info.select("td").get(3).text();
                Pattern pattern2 = Pattern.compile("\\D\\D(\\D)");
                Matcher matcher2 = pattern2.matcher(info.select("td").get(1).text());
                matcher2.find();
                char day = matcher2.group(1).charAt(0);
                switch (day) {
                    case '一':
                        tempWeekDay = 1;
                        break;
                    case '二':
                        tempWeekDay = 2;
                        break;
                    case '三':
                        tempWeekDay = 3;
                        break;
                    case '四':
                        tempWeekDay = 4;
                        break;
                    case '五':
                        tempWeekDay = 5;
                        break;
                    case '六':
                        tempWeekDay = 6;
                        break;
                    case '日':
                        tempWeekDay = 7;
                        break;
                    default:
                        tempWeekDay = 0;
                        break;
                }
                Pattern pattern3 = Pattern.compile("\\D?\\D(\\d+)-(\\d+)节");
                Matcher matcher3 = pattern3.matcher(info.select("td").get(2).text());

                if (matcher3.find()) {
                    tempStratNumber = Integer.parseInt(matcher3.group(1));
                    tempEndNumber = Integer.parseInt(matcher3.group(2));
                } else {
                    Pattern pattern4 = Pattern.compile("\\D?\\D(\\d)(\\d)?(\\d)?(\\d)节");
                    Matcher matcher4 = pattern4.matcher(info.select("td").get(2).text());
                    if (matcher4.find()) {
                        tempStratNumber = Integer.parseInt(matcher4.group(1));
                        tempEndNumber = Integer.parseInt(matcher4.group(4));
                    } else {
                        System.out.println(info.select("td").get(2).text());
                    }

                }

                Pattern pattern1 = Pattern.compile("\\D?(\\d+)-(\\d+)周(\\D)周");
                Matcher matcher1 = pattern1.matcher(info.select("td").get(0).text());
                if (!matcher1.find()) {
                    Pattern pattern0 = Pattern.compile("第(\\d+)-(\\d+)周");
                    Matcher matcher0 = pattern0.matcher(info.select("td").get(0).text());
                    odd = '全';
                    if(matcher0.find()) {
                        tempStartWeek = Integer.parseInt(matcher0.group(1));
                        tempEndWeek = Integer.parseInt(matcher0.group(2));
                    }else {
                        pattern0 = Pattern.compile("(\\d+)");
                        matcher0 = pattern0.matcher(info.select("td").get(0).text());
                        odd = '5';
                        if (matcher0.find()) {
                            tempIsOdd = 3;
                            Course tempCourse = new Course(tempCourseName, tempTeacherName, tempLocation, tempStartWeek, tempEndWeek, tempIsOdd, tempStratNumber, tempEndNumber, tempWeekDay);
                            do {
                                tempCourse.addWeek(Integer.parseInt(matcher0.group()));
                            } while (matcher0.find());
                            courses.add(tempCourse);
                        } else
                            System.out.println(info.select("td").get(0).text());
                    }
                } else {
                    tempStartWeek = Integer.parseInt(matcher1.group(1));
                    tempEndWeek = Integer.parseInt(matcher1.group(2));
                    odd = matcher1.group(3).charAt(0);
                }
                switch (odd) {
                    case '单':
                        tempIsOdd = 1;
                        break;
                    case '双':
                        tempIsOdd = 0;
                        break;
                    case '全':
                        tempIsOdd = 2;
                        break;
                    default:
                        tempIsOdd = 3;
                        break;
                }
                if(!isAdd)
                    courses.add(new Course(tempCourseName,tempTeacherName,tempLocation,tempStartWeek,tempEndWeek,tempIsOdd,tempStratNumber,tempEndNumber,tempWeekDay));
            }

        }
        return true;
    }

    public static ArrayList<Course> getCourseList(String inputedName,String inputedPasswd,String inputedCode){
        Document tempdocument;
        ArrayList<Course> courses=new ArrayList<>();
        try {
            userName=inputedName;
            userPasswd=inputedPasswd;
            captacha=inputedCode;
            tempdocument=Login();
            tempdocument=findCourse(tempdocument);

            parseCourse(courses,tempdocument);
            for(Course course:courses)
                Log.e("course",course.toString());


        }catch (IOException e){
            System.out.println("IOException");
        }
        return  courses;
    }
}


