package testGoogle.test;

import org.testng.annotations.Test;
import testGoogle.framework.BaseTest;

/**
 * Created by jayachk on 2/13/16.
 */
public class WWEPPVHomeTest extends BaseTest{

    @Test(groups = {"smoke"})
    public void doGoogleSearch() throws Exception {

        wikiPPV().getAllPPV();

    }

    @Test(groups = {"smoke"})
    public void doPlayers() throws Exception {

        wikiPPV().splitPlayers();

    }

}
