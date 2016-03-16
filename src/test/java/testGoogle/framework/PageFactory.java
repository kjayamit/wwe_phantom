package testGoogle.framework;

import testGoogle.pages.WikiPPV;


public class PageFactory {

    private static WikiPPV wikiPPV;


    public static void initializePageObjects() {

        wikiPPV = new WikiPPV();
    }

    /**
     * Getter Methods**
     */

    public static WikiPPV wikiPPV() {
        return wikiPPV;
    }
}
