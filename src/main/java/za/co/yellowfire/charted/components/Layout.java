package za.co.yellowfire.charted.components;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.SymbolConstants;
import za.co.yellowfire.charted.domain.MenuSection;

/**
 * Layout component for pages of application tapestry.
 */
@Import(stack={"core"}, stylesheet = {"context:css/style.css"})
public class Layout {
    /**
     * The page title, for the <title> element and the <h1> element.
     */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private MenuSection moduleSection;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String sidebarTitle;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block menu;

    @Property
    @Inject
    @Symbol(SymbolConstants.APPLICATION_VERSION)
    private String appVersion;

    public MenuSection[] getModuleSections() {
        return new MenuSection[] {
                new MenuSection("Budget", "budget/index")
        };
    }

//    public ValueEncoder<MenuSection> getSectionEncoder() {
//        return new ValueEncoder<MenuSection>() {
//            @Override
//            public String toClient(MenuSection menuSection) {
//                return menuSection.getName();
//            }
//
//            @Override
//            public MenuSection toValue(String s) {
//                return new MenuSection(s, new String[0]);
//            }
//        };
//    }
}
