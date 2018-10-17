package AppFramework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Locator {
    private By by;
    private String name;
    private WebElement element;


    public Locator(By by, String name) {
        super();
        this.by = by;
        this.name = name;
        this.element = null;

    }

    public Locator(WebElement element, By by, String name) {
        super();
        this.by = by;
        this.name = name;
        this.element = element;
    }

    public By getBy() {
        return by;
    }

    public void setBy(By by) {
        this.by = by;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WebElement getElement() {
        return element;
    }

}
