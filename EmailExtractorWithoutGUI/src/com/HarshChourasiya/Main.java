package com.HarshChourasiya;

import java.io.BufferedWriter;

import org.apache.commons.exec.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static ArrayList<String> emailExtractText = new ArrayList<>();
    public static ArrayList<String> emails = new ArrayList<>();
    public static BufferedWriter writer = null;
    public static String[] socialMedia = {"linkedin.com", "facebook.com", "instagram.com", "youtube.com", "twitter.com", "reddit.com", "pinterest.com", "medium.com", "Quora.com"};
    public static String[] searchEngine = {"https://in.yahoo.com/everything/", "https://www.bing.com/", "https://www.google.com/"};
    public static String[] domainsName = {"gmail.com", "yahoo.com", "hotmail.com", "aol.com", "outlook.com"};


    public static void main(String[] args) {
        System.setProperty("Webdrive.chrome.driver", "G:\\3.Harsh\\harshChourasiyapersonal\\Java_Madules\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        String toGetCurrentUrl = null;
        WebDriver driver = new ChromeDriver(options);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Your Keyword: ");
        String pro1 = scanner.nextLine();
        System.out.print("Enter Your Country: ");
        String country1 = scanner.next();
        for (int i = 0; i < socialMedia.length; i++) {
            StringBuilder url = new StringBuilder();
            url.append("site:" + socialMedia[i] + " \"" + pro1 + "\" " + "\"" + country1 + "\"");
            for (int j = 0; j < searchEngine.length; j++) {
                driver.get(searchEngine[j]);
                try {
                    Thread.sleep(1000);
                } catch
                (Exception e) {
                    e.getMessage();
                }
                if (searchEngine[j].equals("https://www.google.com/")) {
                    for (int x = 0; x < domainsName.length; x++) {
                        driver.get(searchEngine[j]);
                        StringBuilder urlForGoogle = new StringBuilder();
                        urlForGoogle.append(url);
                        urlForGoogle.append(" \"" + domainsName[x] + "\"");
                        try {
                            WebElement input = driver.findElement(By.xpath("//input[@type='text']"));
                            input.sendKeys(urlForGoogle + "\n");
                            for (int y = 0; y < 10; y++) {
                                try {
                                    java.util.List<WebElement> text = driver.findElements(By.xpath("//span[@class='st']"));
                                    for (int u = 0; u < text.size(); u++) {
                                        try {
                                            try {
                                                Thread.sleep(1000);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            toGetCurrentUrl = driver.getCurrentUrl();
                                            emailExtractText.add(text.get(u).getText());
                                            toExtractEmailFromText();
                                        } catch (Exception e) {
                                            System.out.println("Something wrong in adding item!");
                                            e.printStackTrace();
                                            continue;
                                        }
                                    }
                                    try {
                                        Thread.sleep(1000);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } catch (Exception e) {
                                    try {
                                        Thread.sleep(1 * 60 * 1000);
                                    } catch (Exception b) {
                                        b.printStackTrace();
                                    }
                                    driver.close();
                                    driver = new ChromeDriver();
                                    driver.navigate().to(toGetCurrentUrl);
                                    try {
                                        Thread.sleep(1 * 60 * 1000);
                                    } catch (Exception b) {
                                        b.printStackTrace();
                                    }
                                }
                                try {
                                    driver.findElement(By.xpath("//*[@id=\"pnnext\"]/span[2]")).click();
                                } catch (Exception e) {
                                    try {
                                        Thread.sleep(1 * 60 * 1000);
                                    } catch (Exception b) {
                                        b.printStackTrace();
                                    }
                                    driver.close();
                                    driver = new ChromeDriver();
                                    driver.navigate().to(toGetCurrentUrl);
                                    try {
                                        Thread.sleep(1 * 60 * 1000);
                                    } catch (Exception b) {
                                        b.printStackTrace();
                                    }
                                    System.out.println("Something wrong in google click");
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Something wrong in whole google ");
                            e.printStackTrace();

                        }
                    }
                } else if (searchEngine[j].equals("https://www.bing.com/")) {
                    for (int x = 0; x < domainsName.length; x++) {
                        driver.get(searchEngine[j]);
                        StringBuilder urlForBing = new StringBuilder();
                        urlForBing.append(url);
                        urlForBing.append(" \"" + domainsName[x] + "\"");
                        try {
                            WebElement input = driver.findElement(By.xpath("//input[@type='search'and@name='q']"));
                            input.sendKeys(urlForBing + "\n");
                            Thread.sleep(500);

                            for (int y = 0; y < 10; y++) {
                                try {
                                    java.util.List<WebElement> text = driver.findElements(By.xpath("//p"));
                                    for (int u = 0; u < text.size(); u++) {
                                        try {
                                            emailExtractText.add(text.get(u).getText());
                                            toExtractEmailFromText();
                                        } catch (Exception e) {
                                            System.out.println("Something wrong in adding item!");
                                            e.printStackTrace();
                                            continue;
                                        }
                                    }
                                    Thread.sleep(1000);
                                } catch (Exception e) {
                                    System.out.println("Something wrong in bing list!");
                                    e.printStackTrace();
                                }
                                try {
                                    WebElement nextButtons = driver.findElement(By.xpath("//a[@title='Next page']"));
                                    nextButtons.click();
                                } catch (Exception e) {
                                    driver.findElement(By.xpath("//*[@id=\"bnp_hfly_cta2\"]")).click();
                                    try {
                                        Thread.sleep(1000);
                                    } catch (Exception b) {
                                        b.printStackTrace();
                                    }
                                    WebElement nextButtons = driver.findElement(By.xpath("//a[@title='Next page']"));
                                    nextButtons.click();
                                    System.out.println("Something went wrog in bing driver next click!");
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Something wrong in whole bing");
                            e.printStackTrace();
                        }
                    }
                } else if (searchEngine[j].equals("https://in.yahoo.com/everything/")) {
                    for (int x = 0; x < domainsName.length; x++) {
                        driver.get(searchEngine[j]);
                        StringBuilder urlForyahoo = new StringBuilder();
                        urlForyahoo.append(url);
                        urlForyahoo.append(" \"" + domainsName[x] + "\"");
                        try {
                            WebElement input = driver.findElement(By.xpath("//input[@type='text']"));
                            input.sendKeys(urlForyahoo + "\n");
                            try {
                                Thread.sleep(500);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                            for (int y = 0; y < 10; y++) {
                                try {
                                    List<WebElement> text = driver.findElements(By.xpath("//p"));
                                    for (int u = 0; u < text.size(); u++) {
                                        try {
                                            emailExtractText.add(text.get(u).getText());
                                            toExtractEmailFromText();
                                        } catch (Exception e) {
                                            System.out.println("Something wrong in adding item!");
                                            e.printStackTrace();
                                            continue;
                                        }
                                    }
                                    try {
                                        Thread.sleep(1000);
                                    } catch (Exception e) {
                                        e.getMessage();
                                    }
                                } catch (Exception e) {
                                    System.out.println("SomeThing wrong in yahoo list!");
                                    e.printStackTrace();
                                }
                                try {
                                    driver.findElement(By.xpath("//a[@class='next']")).click();
                                } catch (Exception e) {
                                    System.out.println("SomeThing Went wrong in yahoo click next button!");
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Something went wrong in whole yahoo!");
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void toExtractEmailFromText() {
        for (int i = 0; i < emailExtractText.size(); i++) {
            try {
                Pattern pattern = Pattern.compile("[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9-.]+", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(emailExtractText.get(i));
                while (matcher.find()) {
                    if (!emails.contains(matcher.group().trim())) {
                        if (!matcher.group().trim().endsWith(".")){
                            emails.add(matcher.group().trim());
                            System.out.println(String.valueOf(emails.size()) + ".  " + matcher.group().trim());
                        }else if(matcher.group().trim().endsWith("")){
                            String name = matcher.group().trim().substring(0, matcher.group().trim().length()-1);
                            emails.add(name);
                            System.out.println(String.valueOf(emails.size()) + ".  " + name.trim());

                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Wrong in extracting emails ");
                e.printStackTrace();
            }
        }
    }

    public static void toSaveTxt() {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("EmailsList.txt")));
        } catch (Exception e) {
            e.getStackTrace();
        }
        for (int i = 0; i < emails.size(); i++) {
            try {
                writer.append(emails.get(i) + "\n");
                writer.flush();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }
}

