package com.example.demo;

import java.io.FileInputStream;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@SpringBootApplication
public class ParsingApplication {

    public static void main(String[] args) {
        // SpringApplication.run(ParsingApplication.class, args);
        String apiUrl = "https://apis.data.go.kr/1613000/AptListService2/getTotalAptList";
        String serviceKey = "SNx2RBBbT2OfWCaXTrFB5BG8jLJ%2FEOlcO8wUs2N%2B8iQgZcdPUTc5Eghg0NN%2FxDHHqm2qM222Gji6oClo8Y4vCw%3D%3D";
        int pageNo = 1;
        int numOfRows = 100; // 한 페이지에 표시할 데이터 수
        boolean hasNextPage = true;

        StringBuilder sb = new StringBuilder();
        sb.append("insert into apartment_information values");

        try {
            while (hasNextPage) {
                String requestUrl = String.format("%s?ServiceKey=%s&pageNo=%d&numOfRows=%d", apiUrl, serviceKey, pageNo, numOfRows);

                // HTTP 요청을 보냅니다.
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(requestUrl))
                        .header("Accept", "application/xml")
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // 응답 XML을 파싱합니다.
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader(response.body())));

                // XML 데이터를 처리합니다.
                NodeList nodeList = document.getElementsByTagName("item");

                if (nodeList.getLength() > 0) {
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Node node = nodeList.item(i);

                        // 'item' 노드가 Element 타입인지 확인
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) node;
//                            System.out.println(element.getTextContent());

                            // 새로운 태그에 맞게 값 가져오기
                            String as1 = null; // 초기값을 null로 설정
                            NodeList as1NodeList = element.getElementsByTagName("as1");
                            if (as1NodeList.getLength() > 0) {
                                as1 = as1NodeList.item(0).getTextContent();
                            }

                            String as2 = null; // 초기값을 null로 설정
                            NodeList as2NodeList = element.getElementsByTagName("as2");
                            if (as2NodeList.getLength() > 0) {
                                as2 = as2NodeList.item(0).getTextContent();
                            }

                            String as3 = null; // 초기값을 null로 설정
                            NodeList as3NodeList = element.getElementsByTagName("as3");
                            if (as3NodeList.getLength() > 0) {
                                as3 = as3NodeList.item(0).getTextContent();
                            }

                            String as4 = null; // 초기값을 null로 설정
                            NodeList as4NodeList = element.getElementsByTagName("as4");
                            if (as4NodeList.getLength() > 0) {
                                as4 = as4NodeList.item(0).getTextContent();
                            }

                            String bjdCode = null; // 초기값을 null로 설정
                            NodeList bjdCodeNodeList = element.getElementsByTagName("bjdCode");
                            if (bjdCodeNodeList.getLength() > 0) {
                                bjdCode = bjdCodeNodeList.item(0).getTextContent();
                            }

                            String kaptCode = null; // 초기값을 null로 설정
                            NodeList kaptCodeNodeList = element.getElementsByTagName("kaptCode");
                            if (kaptCodeNodeList.getLength() > 0) {
                                kaptCode = kaptCodeNodeList.item(0).getTextContent();
                            }

                            String kaptName = null; // 초기값을 null로 설정
                            NodeList kaptNameNodeList = element.getElementsByTagName("kaptName");
                            if (kaptNameNodeList.getLength() > 0) {
                                kaptName = kaptNameNodeList.item(0).getTextContent();
                            }

                            // 가져온 값들을 출력
                            if (i == nodeList.getLength() - 1) {
                                sb.append("(").append("'").append(as1).append("'").append(",").append("'").append(as2)
                                        .append("'").append(",").append("'").append(as3).append("'").append(",").append("'")
                                        .append(as4).append("'").append(",").append("'").append(bjdCode).append("'").append(",")
                                        .append("'").append(kaptCode).append("'").append(",").append("'").append(kaptName)
                                        .append("'").append(")").append(";").append("\n");
                                break;
                            }
                            sb.append("(").append("'").append(as1).append("'").append(",").append("'").append(as2).append("'")
                                    .append(",").append("'").append(as3).append("'").append(",").append("'").append(as4)
                                    .append("'").append(",").append("'").append(bjdCode).append("'").append(",").append("'")
                                    .append(kaptCode).append("'").append(",").append("'").append(kaptName).append("'")
                                    .append(")").append(",").append("\n");
                        }
                    }
                    pageNo++;
                } else {
                    hasNextPage = false;
                }
            }
//            System.out.println(sb.toString());
            // Save StringBuilder content to a file
            Path filePath = Paths.get("src/main/resources/res/apartment_information.sql");
            Files.writeString(filePath, sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("File saved to " + filePath.toAbsolutePath());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
