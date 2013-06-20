package com.virtual7.portalPPR.portal;

import java.util.List;

import oracle.webcenter.navigationframework.NavigationContext;
import oracle.webcenter.navigationframework.NavigationModel;
import oracle.webcenter.navigationframework.NavigationResource;
import oracle.webcenter.portalframework.sitestructure.SiteStructureContext;
import oracle.webcenter.portalframework.sitestructure.SiteStructureResource;


public class PortalMB {

    private static final String NAV_RES_TITLE_HOME = "home";
    private static final String NAV_RES_TITLE_PAGE1 = "page1";
    private static final String NAV_RES_TITLE_PAGE2 = "page2";

    private NavigationResource homeItem = null;
    private String homeUrl = null;

    private NavigationResource page1Item = null;
    private String page1Url = null;

    private NavigationResource page2Item = null;
    private String page2Url = null;

    public PortalMB() {
        super();
    }

    /**
     * Lookup the navigation restouce by the title.
     *
     * @param title the title.
     * @return a NavigationResource
     */
    public static NavigationResource findNavResourceByTitle(final String title) {
        NavigationResource navRes = null;
        final NavigationContext navContext = SiteStructureContext.getInstance();
        if (title != null && navContext != null && navContext.getCurrentNavigationModel() != null) {
            final NavigationModel navModel = navContext.getCurrentNavigationModel();
            final List<NavigationResource> navModeResList = navModel.getDefaultListModel();

            if (navModeResList != null && navModeResList.size() > 0 && navModeResList.get(0) != null &&
                SiteStructureResource.class.isInstance(navModeResList.get(0))) {
                final SiteStructureResource siteStructRes = (SiteStructureResource)navModeResList.get(0);
                final List<NavigationResource> resources = siteStructRes.getChildren();

                // Lookup page.
                if (resources != null) {
                    for (NavigationResource res : resources) {
                        if (res != null && title.equals(res.getTitle())) {
                            navRes = res;
                        }
                    }
                }
            }
        }
        return navRes;
    }

    public NavigationResource getHomeItem() {
        if (homeItem == null) {
            homeItem = findNavResourceByTitle(NAV_RES_TITLE_HOME);
        }
        return homeItem;
    }

    public String getHomeUrl() {
        if (homeUrl == null) {
            NavigationResource item = getHomeItem();
            if (item != null) {
                homeUrl = item.getGoLinkPrettyUrl();
            }
        }
        return homeUrl;
    }

    public NavigationResource getPage1Item() {
        if (page1Item == null) {
            page1Item = findNavResourceByTitle(NAV_RES_TITLE_PAGE1);
        }
        return page1Item;
    }

    public String getPage1Url() {
        if (page1Url == null) {
            NavigationResource item = getPage1Item();
            if (item != null) {
                page1Url = item.getGoLinkPrettyUrl();
            }
        }
        return page1Url;
    }

    public NavigationResource getPage2Item() {
        if (page2Item == null) {
            page2Item = findNavResourceByTitle(NAV_RES_TITLE_PAGE2);
        }
        return page2Item;
    }

    public String getPage2Url() {
        if (page2Url == null) {
            NavigationResource item = getPage2Item();
            if (item != null) {
                page2Url = item.getGoLinkPrettyUrl();
            }
        }
        return page2Url;
    }
}
