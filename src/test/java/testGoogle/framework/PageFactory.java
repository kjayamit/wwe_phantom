package testGoogle.framework;

import testGoogle.pages.GoogleHomePage;
import testGoogle.pages.WikiPPV;


public class PageFactory {

    private static GoogleHomePage googleHomePage;
    private static WikiPPV wikiPPV;


    public static void initializePageObjects() {

        googleHomePage = new GoogleHomePage();
        wikiPPV = new WikiPPV();
    }

    /**
     * Getter Methods**
     */
    public static GoogleHomePage googleHomePage() {
        return googleHomePage;
    }

    public static WikiPPV wikiPPV() {
        return wikiPPV;
    }
}
