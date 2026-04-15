package DAY_17.task.com.Url_parser.service;

import DAY_17.task.com.Url_parser.model.UrlData;

public class UrlService {

    public void showDetails(UrlData urlData) {
        if (urlData != null) {
            urlData.display();
        } else {
            System.out.println("No URL data to display.");
        }
    }

}
