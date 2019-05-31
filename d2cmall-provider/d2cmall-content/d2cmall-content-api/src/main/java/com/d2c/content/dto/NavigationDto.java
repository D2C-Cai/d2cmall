package com.d2c.content.dto;

import com.d2c.content.model.Navigation;
import com.d2c.content.model.NavigationItem;

import java.util.List;

public class NavigationDto extends Navigation {

    private static final long serialVersionUID = 1L;
    private List<NavigationDto> children;
    private List<NavigationItem> navigationItems;

    public List<NavigationDto> getChildren() {
        return children;
    }

    public void setChildren(List<NavigationDto> children) {
        this.children = children;
    }

    public List<NavigationItem> getNavigationItems() {
        return navigationItems;
    }

    public void setNavigationItems(List<NavigationItem> navigationItems) {
        this.navigationItems = navigationItems;
    }

}
